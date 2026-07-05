package com.example.demo.model;

import java.time.LocalDate;

public class GoalBo {
    private Long id;
    private String title;
    private String description;
    private String type;
    private Double targetValue;
    private Double currentValue;
    private LocalDate targetDate;
    private boolean achieved;

    private Long relatedRaceId;
    private String relatedRaceName; // convenience, read-only

    public GoalBo() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public Double getTargetValue() { return targetValue; }
    public void setTargetValue(Double targetValue) { this.targetValue = targetValue; }

    public Double getCurrentValue() { return currentValue; }
    public void setCurrentValue(Double currentValue) { this.currentValue = currentValue; }

    public LocalDate getTargetDate() { return targetDate; }
    public void setTargetDate(LocalDate targetDate) { this.targetDate = targetDate; }

    public boolean isAchieved() { return achieved; }
    public void setAchieved(boolean achieved) { this.achieved = achieved; }

    public Long getRelatedRaceId() { return relatedRaceId; }
    public void setRelatedRaceId(Long relatedRaceId) { this.relatedRaceId = relatedRaceId; }

    public String getRelatedRaceName() { return relatedRaceName; }
    public void setRelatedRaceName(String relatedRaceName) { this.relatedRaceName = relatedRaceName; }
}
