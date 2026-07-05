package com.example.demo.service;

import com.example.demo.mapper.RaceMapper;
import com.example.demo.model.Race;
import com.example.demo.model.RaceBo;
import com.example.demo.repository.RaceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RaceServiceImpl implements RaceService {

    private final RaceRepository raceRepository;
    private final RaceMapper raceMapper;

    public RaceServiceImpl(RaceRepository raceRepository, RaceMapper raceMapper) {
        this.raceRepository = raceRepository;
        this.raceMapper = raceMapper;
    }

    @Override
    public RaceBo save(RaceBo bo) {
        Race entity = raceMapper.toEntity(bo);
        return raceMapper.toBo(raceRepository.save(entity));
    }

    @Override
    public RaceBo update(Long id, RaceBo bo) {
        raceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Race not found"));
        Race updated = raceMapper.toEntity(bo);
        updated.setId(id);
        return raceMapper.toBo(raceRepository.save(updated));
    }

    @Override
    public List<RaceBo> findAll() {
        return raceRepository.findAllByOrderByRaceDateDesc().stream()
                .map(raceMapper::toBo)
                .collect(Collectors.toList());
    }

    @Override
    public RaceBo findById(Long id) {
        return raceRepository.findById(id)
                .map(raceMapper::toBo)
                .orElseThrow(() -> new RuntimeException("Race not found"));
    }

    @Override
    public void delete(Long id) {
        raceRepository.deleteById(id);
    }
}
