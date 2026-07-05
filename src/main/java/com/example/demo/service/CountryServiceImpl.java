package com.example.demo.service;

import com.example.demo.mapper.CountryMapper;
import com.example.demo.model.Country;
import com.example.demo.model.CountryBo;
import com.example.demo.repository.CountryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;
    private final CountryMapper countryMapper;

    public CountryServiceImpl(CountryRepository countryRepository, CountryMapper countryMapper) {
        this.countryRepository = countryRepository;
        this.countryMapper = countryMapper;
    }

    @Override
    public CountryBo save(CountryBo bo) {
        Country entity = countryMapper.toEntity(bo);
        return countryMapper.toBo(countryRepository.save(entity));
    }

    @Override
    public CountryBo update(Long id, CountryBo bo) {
        Country existing = countryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Country not found"));
        existing.setName(bo.getName());
        existing.setIsoCode(bo.getIsoCode());
        return countryMapper.toBo(countryRepository.save(existing));
    }

    @Override
    public List<CountryBo> findAll() {
        return countryRepository.findAll().stream()
                .map(countryMapper::toBo)
                .collect(Collectors.toList());
    }

    @Override
    public CountryBo findById(Long id) {
        return countryRepository.findById(id)
                .map(countryMapper::toBo)
                .orElseThrow(() -> new RuntimeException("Country not found"));
    }

    @Override
    public void delete(Long id) {
        countryRepository.deleteById(id);
    }
}
