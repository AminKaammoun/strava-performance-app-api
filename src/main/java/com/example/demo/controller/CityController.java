package com.example.demo.controller;

import com.example.demo.model.CityBo;
import com.example.demo.service.CityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/cities")
public class CityController extends GenericRestController<CityBo, Long, CityService> {

    public CityController(CityService cityService) {
        super(cityService);
    }

    @Override
    protected CityService getService() {
        return service;
    }

    // GET /api/cities/by-country?countryId=1
    @GetMapping("/by-country")
    public ResponseEntity<List<CityBo>> findByCountry(@RequestParam Long countryId) {
        return ResponseEntity.ok(service.findByCountryId(countryId));
    }
}
