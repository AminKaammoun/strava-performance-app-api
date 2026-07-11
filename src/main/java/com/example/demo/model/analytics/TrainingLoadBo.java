package com.example.demo.model.analytics;

import java.util.List;

public class TrainingLoadBo {
    private List<WeeklyLoadPointBo> weeklyLoad;
    private double acuteLoad7d;
    private double chronicLoadAvgWeekly28d;
    private Double acuteChronicRatio;
    private int restDaysLast28;

    public List<WeeklyLoadPointBo> getWeeklyLoad() { return weeklyLoad; }
    public void setWeeklyLoad(List<WeeklyLoadPointBo> weeklyLoad) { this.weeklyLoad = weeklyLoad; }

    public double getAcuteLoad7d() { return acuteLoad7d; }
    public void setAcuteLoad7d(double acuteLoad7d) { this.acuteLoad7d = acuteLoad7d; }

    public double getChronicLoadAvgWeekly28d() { return chronicLoadAvgWeekly28d; }
    public void setChronicLoadAvgWeekly28d(double chronicLoadAvgWeekly28d) { this.chronicLoadAvgWeekly28d = chronicLoadAvgWeekly28d; }

    public Double getAcuteChronicRatio() { return acuteChronicRatio; }
    public void setAcuteChronicRatio(Double acuteChronicRatio) { this.acuteChronicRatio = acuteChronicRatio; }

    public int getRestDaysLast28() { return restDaysLast28; }
    public void setRestDaysLast28(int restDaysLast28) { this.restDaysLast28 = restDaysLast28; }
}
