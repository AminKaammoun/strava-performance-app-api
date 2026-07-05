package com.example.demo.controller;

import com.example.demo.model.RaceBo;
import com.example.demo.service.RaceService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/races")
public class RaceController extends GenericRestController<RaceBo, Long, RaceService> {

    public RaceController(RaceService raceService) {
        super(raceService);
    }

    @Override
    protected RaceService getService() {
        return service;
    }
}
