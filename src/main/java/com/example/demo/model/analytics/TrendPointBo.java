package com.example.demo.model.analytics;

import java.time.LocalDate;

public class TrendPointBo {
    private LocalDate weekStart;
    private String label;
    private double distanceKm;
    private long movingTimeMin;
    private Double avgPaceSecPerKm;
    private double elevationGainM;
    private Double avgHeartrate;
    private int activityCount;

    public LocalDate getWeekStart() { return weekStart; }
    public void setWeekStart(LocalDate weekStart) { this.weekStart = weekStart; }

    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }

    public double getDistanceKm() { return distanceKm; }
    public void setDistanceKm(double distanceKm) { this.distanceKm = distanceKm; }

    public long getMovingTimeMin() { return movingTimeMin; }
    public void setMovingTimeMin(long movingTimeMin) { this.movingTimeMin = movingTimeMin; }

    public Double getAvgPaceSecPerKm() { return avgPaceSecPerKm; }
    public void setAvgPaceSecPerKm(Double avgPaceSecPerKm) { this.avgPaceSecPerKm = avgPaceSecPerKm; }

    public double getElevationGainM() { return elevationGainM; }
    public void setElevationGainM(double elevationGainM) { this.elevationGainM = elevationGainM; }

    public Double getAvgHeartrate() { return avgHeartrate; }
    public void setAvgHeartrate(Double avgHeartrate) { this.avgHeartrate = avgHeartrate; }

    public int getActivityCount() { return activityCount; }
    public void setActivityCount(int activityCount) { this.activityCount = activityCount; }
}
