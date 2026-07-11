package com.example.demo.controller.analytics;

import com.example.demo.model.analytics.DashboardSummaryBo;
import com.example.demo.model.analytics.PersonalRecordBo;
import com.example.demo.model.analytics.ShoeMileageBo;
import com.example.demo.model.analytics.TrainingLoadBo;
import com.example.demo.model.analytics.TrendPointBo;
import com.example.demo.service.analytics.AnalyticsService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    public AnalyticsController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @GetMapping("/summary")
    public ResponseEntity<DashboardSummaryBo> getSummary() {
        return ResponseEntity.ok(analyticsService.getSummary());
    }

    @GetMapping("/trends")
    public ResponseEntity<List<TrendPointBo>> getTrends(@RequestParam(defaultValue = "12") int weeks) {
        return ResponseEntity.ok(analyticsService.getTrends(weeks));
    }

    @GetMapping("/records")
    public ResponseEntity<List<PersonalRecordBo>> getRecords() {
        return ResponseEntity.ok(analyticsService.getRecords());
    }

    @GetMapping("/shoes")
    public ResponseEntity<List<ShoeMileageBo>> getShoeMileage() {
        return ResponseEntity.ok(analyticsService.getShoeMileage());
    }

    @GetMapping("/training-load")
    public ResponseEntity<TrainingLoadBo> getTrainingLoad(@RequestParam(defaultValue = "8") int weeks) {
        return ResponseEntity.ok(analyticsService.getTrainingLoad(weeks));
    }
}
