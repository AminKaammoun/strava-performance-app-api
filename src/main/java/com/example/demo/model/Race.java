package com.example.demo.model;

import com.example.demo.model.Strava.StravaActivity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

@Entity
public class Race {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    private LocalDate raceDate;
    private Double distanceKm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    private City city;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shoe_id")
    private Shoe shoe;

    // Optional link to the actual synced Strava activity for this race —
    // gives access to real pace/heart-rate/elevation data recorded that day.
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "strava_activity_id", unique = true)
    private StravaActivity stravaActivity;

    private Long targetTimeSeconds;
    private Long actualTimeSeconds;
    private Integer overallPlacement;
    private Integer categoryPlacement;
    private String bibNumber;
    private String notes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getRaceDate() {
        return raceDate;
    }

    public void setRaceDate(LocalDate raceDate) {
        this.raceDate = raceDate;
    }

    public Double getDistanceKm() {
        return distanceKm;
    }

    public void setDistanceKm(Double distanceKm) {
        this.distanceKm = distanceKm;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Shoe getShoe() {
        return shoe;
    }

    public void setShoe(Shoe shoe) {
        this.shoe = shoe;
    }

    public StravaActivity getStravaActivity() {
        return stravaActivity;
    }

    public void setStravaActivity(StravaActivity stravaActivity) {
        this.stravaActivity = stravaActivity;
    }

    public Long getTargetTimeSeconds() {
        return targetTimeSeconds;
    }

    public void setTargetTimeSeconds(Long targetTimeSeconds) {
        this.targetTimeSeconds = targetTimeSeconds;
    }

    public Long getActualTimeSeconds() {
        return actualTimeSeconds;
    }

    public void setActualTimeSeconds(Long actualTimeSeconds) {
        this.actualTimeSeconds = actualTimeSeconds;
    }

    public Integer getOverallPlacement() {
        return overallPlacement;
    }

    public void setOverallPlacement(Integer overallPlacement) {
        this.overallPlacement = overallPlacement;
    }

    public Integer getCategoryPlacement() {
        return categoryPlacement;
    }

    public void setCategoryPlacement(Integer categoryPlacement) {
        this.categoryPlacement = categoryPlacement;
    }

    public String getBibNumber() {
        return bibNumber;
    }

    public void setBibNumber(String bibNumber) {
        this.bibNumber = bibNumber;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
