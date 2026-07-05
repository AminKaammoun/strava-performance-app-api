package com.example.demo.mapper;

import com.example.demo.model.City;
import com.example.demo.model.CityBo;
import com.example.demo.model.Country;
import com.example.demo.repository.CountryRepository;
import org.springframework.stereotype.Component;

@Component
public class CityMapper {

    private final CountryRepository countryRepository;

    public CityMapper(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public CityBo toBo(City city) {
        CityBo bo = new CityBo();
        bo.setId(city.getId());
        bo.setName(city.getName());
        if (city.getCountry() != null) {
            bo.setCountryId(city.getCountry().getId());
            bo.setCountryName(city.getCountry().getName());
        }
        return bo;
    }

    public City toEntity(CityBo bo) {
        City city = new City();
        city.setId(bo.getId());
        city.setName(bo.getName());
        if (bo.getCountryId() != null) {
            Country country = countryRepository.findById(bo.getCountryId())
                    .orElseThrow(() -> new RuntimeException("Country not found: " + bo.getCountryId()));
            city.setCountry(country);
        }
        return city;
    }
}
