package com.example.demo.model.analytics;

public class ShoeMileageBo {
    private String id;
    private String name;
    private String brand;
    private String type;
    private double distanceKm;
    private double mileageLimitKm;
    private double percentUsed;
    private boolean retired;
    private boolean warning;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public double getDistanceKm() { return distanceKm; }
    public void setDistanceKm(double distanceKm) { this.distanceKm = distanceKm; }

    public double getMileageLimitKm() { return mileageLimitKm; }
    public void setMileageLimitKm(double mileageLimitKm) { this.mileageLimitKm = mileageLimitKm; }

    public double getPercentUsed() { return percentUsed; }
    public void setPercentUsed(double percentUsed) { this.percentUsed = percentUsed; }

    public boolean isRetired() { return retired; }
    public void setRetired(boolean retired) { this.retired = retired; }

    public boolean isWarning() { return warning; }
    public void setWarning(boolean warning) { this.warning = warning; }
}
