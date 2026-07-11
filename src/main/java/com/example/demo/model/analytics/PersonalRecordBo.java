package com.example.demo.model.analytics;

import java.time.LocalDateTime;

public class PersonalRecordBo {
    private String label; // e.g. "5K", "Longest run", "Best pace"
    private Long activityId;
    private String activityName;
    private LocalDateTime date;
    private double distanceKm;
    private long timeSeconds;
    private double paceSecPerKm;

    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }

    public Long getActivityId() { return activityId; }
    public void setActivityId(Long activityId) { this.activityId = activityId; }

    public String getActivityName() { return activityName; }
    public void setActivityName(String activityName) { this.activityName = activityName; }

    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }

    public double getDistanceKm() { return distanceKm; }
    public void setDistanceKm(double distanceKm) { this.distanceKm = distanceKm; }

    public long getTimeSeconds() { return timeSeconds; }
    public void setTimeSeconds(long timeSeconds) { this.timeSeconds = timeSeconds; }

    public double getPaceSecPerKm() { return paceSecPerKm; }
    public void setPaceSecPerKm(double paceSecPerKm) { this.paceSecPerKm = paceSecPerKm; }
}
