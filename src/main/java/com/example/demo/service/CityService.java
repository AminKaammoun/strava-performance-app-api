package com.example.demo.service;

import com.example.demo.model.CityBo;

import java.util.List;

public interface CityService extends GenericCrudService<CityBo, Long> {
    List<CityBo> findByCountryId(Long countryId);
}
