package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

@Entity
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required")
    private String title;

    private String description;
    private String type; // DISTANCE, TIME, RACE_FINISH, WEIGHT, CUSTOM — plain string for flexibility

    private Double targetValue;  // meaning depends on type (km, seconds, kg, etc.)
    private Double currentValue;

    private LocalDate targetDate;
    private boolean achieved = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "related_race_id")
    private Race relatedRace;

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

    public Race getRelatedRace() { return relatedRace; }
    public void setRelatedRace(Race relatedRace) { this.relatedRace = relatedRace; }
}
