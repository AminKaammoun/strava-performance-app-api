package com.example.demo.model.analytics;

import java.time.LocalDate;

public class WeeklyLoadPointBo {
    private LocalDate weekStart;
    private String label;
    private double load;

    public LocalDate getWeekStart() { return weekStart; }
    public void setWeekStart(LocalDate weekStart) { this.weekStart = weekStart; }

    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }

    public double getLoad() { return load; }
    public void setLoad(double load) { this.load = load; }
}
