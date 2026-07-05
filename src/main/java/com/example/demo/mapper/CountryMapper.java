package com.example.demo.mapper;

import com.example.demo.model.Country;
import com.example.demo.model.CountryBo;
import org.springframework.stereotype.Component;

@Component
public class CountryMapper {

    public CountryBo toBo(Country country) {
        return new CountryBo(country.getId(), country.getName(), country.getIsoCode());
    }

    public Country toEntity(CountryBo bo) {
        Country country = new Country();
        country.setId(bo.getId());
        country.setName(bo.getName());
        country.setIsoCode(bo.getIsoCode());
        return country;
    }
}
