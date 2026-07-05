package com.example.demo.controller;

import com.example.demo.model.GoalBo;
import com.example.demo.service.GoalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/goals")
public class GoalController extends GenericRestController<GoalBo, Long, GoalService> {

    public GoalController(GoalService goalService) {
        super(goalService);
    }

    @Override
    protected GoalService getService() {
        return service;
    }

    // GET /api/goals/filter?achieved=false
    @GetMapping("/filter")
    public ResponseEntity<List<GoalBo>> findByAchieved(@RequestParam boolean achieved) {
        return ResponseEntity.ok(service.findByAchieved(achieved));
    }
}
