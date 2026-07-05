package com.example.demo.model;

import java.time.LocalDate;

public class RaceBo {
    private Long id;
    private String name;
    private LocalDate raceDate;
    private Double distanceKm;

    private Long cityId;
    private String cityName; // convenience, read-only
    private String countryName; // convenience, read-only (via city -> country)

    private Long shoeId;
    private String shoeName; // convenience, read-only

    private Long stravaActivityId;

    private Long targetTimeSeconds;
    private Long actualTimeSeconds;
    private Integer overallPlacement;
    private Integer categoryPlacement;
    private String bibNumber;
    private String notes;

    public RaceBo() {
    }

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

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public Long getShoeId() {
        return shoeId;
    }

    public void setShoeId(Long shoeId) {
        this.shoeId = shoeId;
    }

    public String getShoeName() {
        return shoeName;
    }

    public void setShoeName(String shoeName) {
        this.shoeName = shoeName;
    }

    public Long getStravaActivityId() {
        return stravaActivityId;
    }

    public void setStravaActivityId(Long stravaActivityId) {
        this.stravaActivityId = stravaActivityId;
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
