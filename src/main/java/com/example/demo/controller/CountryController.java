package com.example.demo.controller;

import com.example.demo.model.CountryBo;
import com.example.demo.service.CountryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/countries")
public class CountryController extends GenericRestController<CountryBo, Long, CountryService> {

    public CountryController(CountryService countryService) {
        super(countryService);
    }

    @Override
    protected CountryService getService() {
        return service;
    }
}
