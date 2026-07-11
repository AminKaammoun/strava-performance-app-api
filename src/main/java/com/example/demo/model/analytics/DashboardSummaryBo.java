package com.example.demo.model.analytics;

public class DashboardSummaryBo {
    private int totalActivities;
    private double distanceThisWeekKm;
    private double distanceThisMonthKm;
    private double distanceThisYearKm;
    private long movingTimeThisWeekMin;
    private Double avgPaceSecPerKm;
    private double longestRunKm;
    private int currentStreakDays;

    public int getTotalActivities() { return totalActivities; }
    public void setTotalActivities(int totalActivities) { this.totalActivities = totalActivities; }

    public double getDistanceThisWeekKm() { return distanceThisWeekKm; }
    public void setDistanceThisWeekKm(double distanceThisWeekKm) { this.distanceThisWeekKm = distanceThisWeekKm; }

    public double getDistanceThisMonthKm() { return distanceThisMonthKm; }
    public void setDistanceThisMonthKm(double distanceThisMonthKm) { this.distanceThisMonthKm = distanceThisMonthKm; }

    public double getDistanceThisYearKm() { return distanceThisYearKm; }
    public void setDistanceThisYearKm(double distanceThisYearKm) { this.distanceThisYearKm = distanceThisYearKm; }

    public long getMovingTimeThisWeekMin() { return movingTimeThisWeekMin; }
    public void setMovingTimeThisWeekMin(long movingTimeThisWeekMin) { this.movingTimeThisWeekMin = movingTimeThisWeekMin; }

    public Double getAvgPaceSecPerKm() { return avgPaceSecPerKm; }
    public void setAvgPaceSecPerKm(Double avgPaceSecPerKm) { this.avgPaceSecPerKm = avgPaceSecPerKm; }

    public double getLongestRunKm() { return longestRunKm; }
    public void setLongestRunKm(double longestRunKm) { this.longestRunKm = longestRunKm; }

    public int getCurrentStreakDays() { return currentStreakDays; }
    public void setCurrentStreakDays(int currentStreakDays) { this.currentStreakDays = currentStreakDays; }
}
