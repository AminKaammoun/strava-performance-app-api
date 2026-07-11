package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class Shoe {

    // Strava's own gear id (e.g. "g12345678987655") — used as-is so saving
    // re-uses the row (upsert), same pattern as StravaActivity's id.
    @Id
    private String id;

    // --- Fields synced from Strava's /athlete endpoint (summary gear) ---
    // These are overwritten every sync; don't hand-edit them, they won't stick.
    private String name; // the nickname the athlete gave it in Strava, e.g. "adidas"

    @Column(name = "is_primary")
    private Boolean primary;
    private Integer resourceState;
    private Double distanceMeters; // total logged distance, straight from Strava

    // --- Manual/local-only fields — Strava's summary gear response doesn't
    // include these, so they're yours to fill in and are left untouched by sync ---
    private String brand;
    private String type; // SHOES, BIKE, WATCH, OTHER — kept as a plain string for flexibility
    private LocalDate purchaseDate;
    private boolean retired = false;
    private String notes;

    // Distance at which this shoe should be considered for retirement. Nullable —
    // AnalyticsService falls back to a sensible default (600km) when not set.
    private Double mileageLimitKm;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getPrimary() {
        return primary;
    }

    public void setPrimary(Boolean primary) {
        this.primary = primary;
    }

    public Integer getResourceState() {
        return resourceState;
    }

    public void setResourceState(Integer resourceState) {
        this.resourceState = resourceState;
    }

    public Double getDistanceMeters() {
        return distanceMeters;
    }

    public void setDistanceMeters(Double distanceMeters) {
        this.distanceMeters = distanceMeters;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public boolean isRetired() {
        return retired;
    }

    public void setRetired(boolean retired) {
        this.retired = retired;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Double getMileageLimitKm() {
        return mileageLimitKm;
    }

    public void setMileageLimitKm(Double mileageLimitKm) {
        this.mileageLimitKm = mileageLimitKm;
    }
}
