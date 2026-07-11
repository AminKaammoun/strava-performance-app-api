package com.example.demo.service.analytics;

import com.example.demo.model.Shoe;
import com.example.demo.model.Strava.StravaActivity;
import com.example.demo.model.analytics.DashboardSummaryBo;
import com.example.demo.model.analytics.PersonalRecordBo;
import com.example.demo.model.analytics.ShoeMileageBo;
import com.example.demo.model.analytics.TrainingLoadBo;
import com.example.demo.model.analytics.TrendPointBo;
import com.example.demo.model.analytics.WeeklyLoadPointBo;
import com.example.demo.repository.ShoeRepository;
import com.example.demo.repository.Strava.StravaActivityRepository;

import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * Read-only aggregations over already-synced Strava data. Never calls the
 * external Strava API and never writes — pure derived reporting.
 */
@Service
public class AnalyticsService {

    private static final double DEFAULT_SHOE_LIMIT_KM = 600.0;
    private static final double WARNING_THRESHOLD = 0.9;

    private static final Map<String, Double> STANDARD_DISTANCES_KM = new LinkedHashMap<>();
    static {
        STANDARD_DISTANCES_KM.put("1K", 1.0);
        STANDARD_DISTANCES_KM.put("5K", 5.0);
        STANDARD_DISTANCES_KM.put("10K", 10.0);
        STANDARD_DISTANCES_KM.put("Half Marathon", 21.0975);
        STANDARD_DISTANCES_KM.put("Marathon", 42.195);
    }

    private final StravaActivityRepository activityRepository;
    private final ShoeRepository shoeRepository;

    public AnalyticsService(StravaActivityRepository activityRepository, ShoeRepository shoeRepository) {
        this.activityRepository = activityRepository;
        this.shoeRepository = shoeRepository;
    }

    public DashboardSummaryBo getSummary() {
        List<StravaActivity> activities = activityRepository.findAllByOrderByStartDateLocalDesc();
        DashboardSummaryBo summary = new DashboardSummaryBo();
        summary.setTotalActivities(activities.size());

        LocalDate today = LocalDate.now();
        LocalDate weekStart = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate monthStart = today.withDayOfMonth(1);
        LocalDate yearStart = today.withDayOfYear(1);

        double distanceWeek = 0, distanceMonth = 0, distanceYear = 0;
        long movingTimeWeekSec = 0;

        for (StravaActivity activity : activities) {
            if (activity.getStartDateLocal() == null || activity.getDistance() == null) continue;
            LocalDate date = activity.getStartDateLocal().toLocalDate();
            double km = activity.getDistance() / 1000.0;

            if (!date.isBefore(yearStart)) distanceYear += km;
            if (!date.isBefore(monthStart)) distanceMonth += km;
            if (!date.isBefore(weekStart)) {
                distanceWeek += km;
                movingTimeWeekSec += activity.getMovingTime() != null ? activity.getMovingTime() : 0;
            }
        }

        summary.setDistanceThisWeekKm(round(distanceWeek));
        summary.setDistanceThisMonthKm(round(distanceMonth));
        summary.setDistanceThisYearKm(round(distanceYear));
        summary.setMovingTimeThisWeekMin(movingTimeWeekSec / 60);

        // Avg pace over runs in the trailing 30 days.
        LocalDate last30 = today.minusDays(30);
        List<StravaActivity> recentRuns = activities.stream()
                .filter(this::isRun)
                .filter(a -> a.getStartDateLocal() != null && !a.getStartDateLocal().toLocalDate().isBefore(last30))
                .filter(a -> a.getDistance() != null && a.getDistance() > 0 && a.getMovingTime() != null)
                .toList();
        double totalRunKm = recentRuns.stream().mapToDouble(a -> a.getDistance() / 1000.0).sum();
        long totalRunSec = recentRuns.stream().mapToLong(StravaActivity::getMovingTime).sum();
        summary.setAvgPaceSecPerKm(totalRunKm > 0 ? round(totalRunSec / totalRunKm) : null);

        double longestRun = activities.stream()
                .filter(this::isRun)
                .filter(a -> a.getDistance() != null)
                .mapToDouble(a -> a.getDistance() / 1000.0)
                .max()
                .orElse(0);
        summary.setLongestRunKm(round(longestRun));

        summary.setCurrentStreakDays(computeStreak(activities, today));

        return summary;
    }

    public List<TrendPointBo> getTrends(int weeks) {
        List<StravaActivity> activities = activityRepository.findAllByOrderByStartDateLocalDesc();
        LocalDate currentWeekStart = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

        // Pre-build the ordered set of week buckets (oldest -> newest) so weeks
        // with zero activities still show up on the chart.
        Map<LocalDate, TrendPointBo> buckets = new TreeMap<>();
        for (int i = weeks - 1; i >= 0; i--) {
            LocalDate ws = currentWeekStart.minusWeeks(i);
            TrendPointBo point = new TrendPointBo();
            point.setWeekStart(ws);
            point.setLabel(ws.format(DateTimeFormatter.ofPattern("MMM d")));
            buckets.put(ws, point);
        }

        Map<LocalDate, List<StravaActivity>> grouped = activities.stream()
                .filter(a -> a.getStartDateLocal() != null)
                .filter(a -> !a.getStartDateLocal().toLocalDate().isBefore(currentWeekStart.minusWeeks(weeks - 1)))
                .collect(Collectors.groupingBy(a -> a.getStartDateLocal().toLocalDate()
                        .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))));

        for (Map.Entry<LocalDate, List<StravaActivity>> entry : grouped.entrySet()) {
            TrendPointBo point = buckets.get(entry.getKey());
            if (point == null) continue;

            List<StravaActivity> weekActivities = entry.getValue();
            double distanceKm = weekActivities.stream()
                    .filter(a -> a.getDistance() != null)
                    .mapToDouble(a -> a.getDistance() / 1000.0)
                    .sum();
            long movingSec = weekActivities.stream()
                    .filter(a -> a.getMovingTime() != null)
                    .mapToLong(StravaActivity::getMovingTime)
                    .sum();
            double elevation = weekActivities.stream()
                    .filter(a -> a.getTotalElevationGain() != null)
                    .mapToDouble(StravaActivity::getTotalElevationGain)
                    .sum();
            List<Double> heartrates = weekActivities.stream()
                    .map(StravaActivity::getAverageHeartrate)
                    .filter(hr -> hr != null && hr > 0)
                    .toList();

            point.setDistanceKm(round(distanceKm));
            point.setMovingTimeMin(movingSec / 60);
            point.setElevationGainM(round(elevation));
            point.setActivityCount(weekActivities.size());
            point.setAvgHeartrate(heartrates.isEmpty() ? null
                    : round(heartrates.stream().mapToDouble(Double::doubleValue).average().orElse(0)));
            point.setAvgPaceSecPerKm(distanceKm > 0 ? round(movingSec / distanceKm) : null);
        }

        return new ArrayList<>(buckets.values());
    }

    public List<PersonalRecordBo> getRecords() {
        List<StravaActivity> runs = activityRepository.findAllByOrderByStartDateLocalDesc().stream()
                .filter(this::isRun)
                .filter(a -> a.getDistance() != null && a.getMovingTime() != null && a.getDistance() > 0)
                .toList();

        List<PersonalRecordBo> records = new ArrayList<>();

        for (Map.Entry<String, Double> entry : STANDARD_DISTANCES_KM.entrySet()) {
            double targetKm = entry.getValue();
            double targetMeters = targetKm * 1000;
            double tolerance = targetMeters * 0.03;

            runs.stream()
                    .filter(a -> Math.abs(a.getDistance() - targetMeters) <= tolerance)
                    .min(Comparator.comparingLong(StravaActivity::getMovingTime))
                    .ifPresent(best -> records.add(toRecord(entry.getKey(), best)));
        }

        runs.stream()
                .max(Comparator.comparingDouble(StravaActivity::getDistance))
                .ifPresent(best -> records.add(toRecord("Longest Run", best)));

        runs.stream()
                .filter(a -> a.getDistance() >= 1000)
                .min(Comparator.comparingDouble(a -> a.getMovingTime() / (a.getDistance() / 1000.0)))
                .ifPresent(best -> records.add(toRecord("Best Pace", best)));

        return records;
    }

    public List<ShoeMileageBo> getShoeMileage() {
        return shoeRepository.findAll().stream()
                .map(this::toShoeMileage)
                .sorted(Comparator.comparingDouble(ShoeMileageBo::getPercentUsed).reversed())
                .toList();
    }

    public TrainingLoadBo getTrainingLoad(int weeks) {
        List<StravaActivity> activities = activityRepository.findAllByOrderByStartDateLocalDesc();
        LocalDate today = LocalDate.now();
        LocalDate currentWeekStart = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

        Map<LocalDate, Double> weeklyLoad = new TreeMap<>();
        for (int i = weeks - 1; i >= 0; i--) {
            weeklyLoad.put(currentWeekStart.minusWeeks(i), 0.0);
        }

        Map<LocalDate, Double> dailyLoad = new LinkedHashMap<>();
        LocalDate windowStart = currentWeekStart.minusWeeks(weeks - 1);

        for (StravaActivity activity : activities) {
            if (activity.getStartDateLocal() == null) continue;
            LocalDate date = activity.getStartDateLocal().toLocalDate();
            if (date.isBefore(windowStart) || date.isAfter(today)) continue;

            double load = estimateLoad(activity);
            dailyLoad.merge(date, load, Double::sum);

            LocalDate weekBucket = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
            weeklyLoad.computeIfPresent(weekBucket, (k, v) -> v + load);
        }

        List<WeeklyLoadPointBo> points = new ArrayList<>();
        for (Map.Entry<LocalDate, Double> entry : weeklyLoad.entrySet()) {
            WeeklyLoadPointBo point = new WeeklyLoadPointBo();
            point.setWeekStart(entry.getKey());
            point.setLabel(entry.getKey().format(DateTimeFormatter.ofPattern("MMM d")));
            point.setLoad(round(entry.getValue()));
            points.add(point);
        }

        double acuteLoad = sumLoadSince(dailyLoad, today.minusDays(6), today);
        double chronicLoad28 = sumLoadSince(dailyLoad, today.minusDays(27), today);
        double chronicAvgWeekly = chronicLoad28 / 4.0;

        int restDays = 0;
        for (LocalDate d = today.minusDays(27); !d.isAfter(today); d = d.plusDays(1)) {
            if (!dailyLoad.containsKey(d)) restDays++;
        }

        TrainingLoadBo bo = new TrainingLoadBo();
        bo.setWeeklyLoad(points);
        bo.setAcuteLoad7d(round(acuteLoad));
        bo.setChronicLoadAvgWeekly28d(round(chronicAvgWeekly));
        bo.setAcuteChronicRatio(chronicAvgWeekly > 0 ? round(acuteLoad / chronicAvgWeekly) : null);
        bo.setRestDaysLast28(restDays);
        return bo;
    }

    private double sumLoadSince(Map<LocalDate, Double> dailyLoad, LocalDate from, LocalDate to) {
        double total = 0;
        for (LocalDate d = from; !d.isAfter(to); d = d.plusDays(1)) {
            total += dailyLoad.getOrDefault(d, 0.0);
        }
        return total;
    }

    /**
     * Rough per-activity training load: uses Strava's own suffer score when
     * available, otherwise a minutes-weighted-by-intensity estimate. Not a
     * clinical TRIMP calculation — just enough signal to show relative load.
     */
    private double estimateLoad(StravaActivity activity) {
        if (activity.getSufferScore() != null) {
            return activity.getSufferScore();
        }
        double minutes = activity.getMovingTime() != null ? activity.getMovingTime() / 60.0 : 0;
        double intensityFactor = activity.getAverageHeartrate() != null ? activity.getAverageHeartrate() / 100.0 : 1.0;
        return minutes * intensityFactor;
    }

    private ShoeMileageBo toShoeMileage(Shoe shoe) {
        ShoeMileageBo bo = new ShoeMileageBo();
        bo.setId(shoe.getId());
        bo.setName(shoe.getName());
        bo.setBrand(shoe.getBrand());
        bo.setType(shoe.getType());
        bo.setRetired(shoe.isRetired());

        double distanceKm = shoe.getDistanceMeters() != null ? shoe.getDistanceMeters() / 1000.0 : 0;
        double limitKm = shoe.getMileageLimitKm() != null ? shoe.getMileageLimitKm() : DEFAULT_SHOE_LIMIT_KM;
        double percentUsed = limitKm > 0 ? (distanceKm / limitKm) * 100.0 : 0;

        bo.setDistanceKm(round(distanceKm));
        bo.setMileageLimitKm(limitKm);
        bo.setPercentUsed(round(percentUsed));
        bo.setWarning(!shoe.isRetired() && percentUsed >= WARNING_THRESHOLD * 100.0);
        return bo;
    }

    private PersonalRecordBo toRecord(String label, StravaActivity activity) {
        PersonalRecordBo bo = new PersonalRecordBo();
        bo.setLabel(label);
        bo.setActivityId(activity.getId());
        bo.setActivityName(activity.getName());
        bo.setDate(activity.getStartDateLocal());
        double distanceKm = activity.getDistance() / 1000.0;
        bo.setDistanceKm(round(distanceKm));
        bo.setTimeSeconds(activity.getMovingTime());
        bo.setPaceSecPerKm(round(activity.getMovingTime() / distanceKm));
        return bo;
    }

    private boolean isRun(StravaActivity activity) {
        return "Run".equalsIgnoreCase(activity.getType()) || "Run".equalsIgnoreCase(activity.getSportType())
                || "TrailRun".equalsIgnoreCase(activity.getSportType());
    }

    private int computeStreak(List<StravaActivity> activities, LocalDate today) {
        Set<LocalDate> activeDays = activities.stream()
                .filter(a -> a.getStartDateLocal() != null)
                .map(a -> a.getStartDateLocal().toLocalDate())
                .collect(Collectors.toSet());

        // If nothing logged yet today, don't let that alone break an otherwise
        // active streak — start counting from yesterday instead.
        LocalDate cursor = activeDays.contains(today) ? today : today.minusDays(1);
        int streak = 0;
        while (activeDays.contains(cursor)) {
            streak++;
            cursor = cursor.minusDays(1);
        }
        return streak;
    }

    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
