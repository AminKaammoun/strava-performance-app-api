package com.example.demo.service.analytics;

import com.example.demo.model.Shoe;
import com.example.demo.model.Strava.StravaActivity;
import com.example.demo.model.analytics.DashboardSummaryBo;
import com.example.demo.model.analytics.PersonalRecordBo;
import com.example.demo.model.analytics.ShoeMileageBo;
import com.example.demo.model.analytics.TrainingLoadBo;
import com.example.demo.model.analytics.TrendPointBo;
import com.example.demo.repository.ShoeRepository;
import com.example.demo.repository.Strava.StravaActivityRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AnalyticsServiceTest {

    @Mock
    private StravaActivityRepository activityRepository;

    @Mock
    private ShoeRepository shoeRepository;

    private StravaActivity run(long id, LocalDateTime date, double distanceMeters, long movingTimeSec) {
        StravaActivity activity = new StravaActivity();
        activity.setId(id);
        activity.setName("Run " + id);
        activity.setType("Run");
        activity.setSportType("Run");
        activity.setStartDateLocal(date);
        activity.setDistance(distanceMeters);
        activity.setMovingTime(movingTimeSec);
        activity.setAverageHeartrate(150.0);
        return activity;
    }

    @Test
    void summaryComputesDistanceAndStreakFromRecentActivities() {
        LocalDateTime today = LocalDateTime.now().withHour(7);
        List<StravaActivity> activities = List.of(
                run(1, today, 10_000, 3000),                 // today, 10km in 50min
                run(2, today.minusDays(1), 5_000, 1500),      // yesterday
                run(3, today.minusDays(5), 21_000, 6000)      // 5 days ago, longest run
        );
        when(activityRepository.findAllByOrderByStartDateLocalDesc()).thenReturn(activities);

        AnalyticsService service = new AnalyticsService(activityRepository, shoeRepository);
        DashboardSummaryBo summary = service.getSummary();

        assertThat(summary.getTotalActivities()).isEqualTo(3);
        assertThat(summary.getLongestRunKm()).isEqualTo(21.0);
        assertThat(summary.getCurrentStreakDays()).isGreaterThanOrEqualTo(2);
        assertThat(summary.getAvgPaceSecPerKm()).isNotNull();
    }

    @Test
    void trendsBucketActivitiesIntoWeeksIncludingEmptyWeeks() {
        LocalDateTime thisWeek = LocalDateTime.now();
        when(activityRepository.findAllByOrderByStartDateLocalDesc())
                .thenReturn(List.of(run(1, thisWeek, 10_000, 3000)));

        AnalyticsService service = new AnalyticsService(activityRepository, shoeRepository);
        List<TrendPointBo> trends = service.getTrends(4);

        assertThat(trends).hasSize(4);
        assertThat(trends.get(3).getDistanceKm()).isEqualTo(10.0);
        assertThat(trends.get(0).getDistanceKm()).isEqualTo(0.0);
    }

    @Test
    void recordsDetectsClosestMatchToStandardDistance() {
        LocalDateTime today = LocalDateTime.now();
        // ~5K in 25 minutes — well within the 3% tolerance band of 5000m.
        when(activityRepository.findAllByOrderByStartDateLocalDesc())
                .thenReturn(List.of(run(1, today, 5010, 1500)));

        AnalyticsService service = new AnalyticsService(activityRepository, shoeRepository);
        List<PersonalRecordBo> records = service.getRecords();

        assertThat(records).anySatisfy(record -> {
            assertThat(record.getLabel()).isEqualTo("5K");
            assertThat(record.getTimeSeconds()).isEqualTo(1500);
        });
    }

    @Test
    void shoeMileageFlagsWarningPastNinetyPercentOfLimit() {
        Shoe wornShoe = new Shoe();
        wornShoe.setId("g1");
        wornShoe.setName("Old Pegasus");
        wornShoe.setDistanceMeters(560_000.0); // 560km
        wornShoe.setMileageLimitKm(600.0);
        wornShoe.setRetired(false);

        Shoe freshShoe = new Shoe();
        freshShoe.setId("g2");
        freshShoe.setName("New Vaporfly");
        freshShoe.setDistanceMeters(50_000.0);
        freshShoe.setRetired(false); // no limit set -> falls back to default 600km

        when(shoeRepository.findAll()).thenReturn(List.of(wornShoe, freshShoe));

        AnalyticsService service = new AnalyticsService(activityRepository, shoeRepository);
        List<ShoeMileageBo> mileage = service.getShoeMileage();

        ShoeMileageBo worn = mileage.stream().filter(s -> s.getId().equals("g1")).findFirst().orElseThrow();
        ShoeMileageBo fresh = mileage.stream().filter(s -> s.getId().equals("g2")).findFirst().orElseThrow();

        assertThat(worn.isWarning()).isTrue();
        assertThat(fresh.isWarning()).isFalse();
        assertThat(fresh.getMileageLimitKm()).isEqualTo(600.0);
    }

    @Test
    void trainingLoadComputesAcuteChronicRatioAndRestDays() {
        LocalDateTime today = LocalDateTime.now();
        when(activityRepository.findAllByOrderByStartDateLocalDesc())
                .thenReturn(List.of(run(1, today, 10_000, 3000)));

        AnalyticsService service = new AnalyticsService(activityRepository, shoeRepository);
        TrainingLoadBo load = service.getTrainingLoad(4);

        assertThat(load.getWeeklyLoad()).hasSize(4);
        assertThat(load.getRestDaysLast28()).isGreaterThan(0);
        assertThat(load.getAcuteLoad7d()).isGreaterThan(0);
    }
}
