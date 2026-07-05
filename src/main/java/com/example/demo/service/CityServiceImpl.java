package com.example.demo.service;

import com.example.demo.mapper.CityMapper;
import com.example.demo.model.City;
import com.example.demo.model.CityBo;
import com.example.demo.repository.CityRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;
    private final CityMapper cityMapper;

    public CityServiceImpl(CityRepository cityRepository, CityMapper cityMapper) {
        this.cityRepository = cityRepository;
        this.cityMapper = cityMapper;
    }

    @Override
    public CityBo save(CityBo bo) {
        City entity = cityMapper.toEntity(bo);
        return cityMapper.toBo(cityRepository.save(entity));
    }

    @Override
    public CityBo update(Long id, CityBo bo) {
        City existing = cityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("City not found"));
        City updated = cityMapper.toEntity(bo);
        existing.setName(updated.getName());
        existing.setCountry(updated.getCountry());
        return cityMapper.toBo(cityRepository.save(existing));
    }

    @Override
    public List<CityBo> findAll() {
        return cityRepository.findAll().stream()
                .map(cityMapper::toBo)
                .collect(Collectors.toList());
    }

    @Override
    public CityBo findById(Long id) {
        return cityRepository.findById(id)
                .map(cityMapper::toBo)
                .orElseThrow(() -> new RuntimeException("City not found"));
    }

    @Override
    public void delete(Long id) {
        cityRepository.deleteById(id);
    }

    @Override
    public List<CityBo> findByCountryId(Long countryId) {
        return cityRepository.findAllByCountry_Id(countryId).stream()
                .map(cityMapper::toBo)
                .collect(Collectors.toList());
    }
}
