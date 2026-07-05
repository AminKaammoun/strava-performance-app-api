package com.example.demo.service;

import com.example.demo.mapper.GoalMapper;
import com.example.demo.model.Goal;
import com.example.demo.model.GoalBo;
import com.example.demo.repository.GoalRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GoalServiceImpl implements GoalService {

    private final GoalRepository goalRepository;
    private final GoalMapper goalMapper;

    public GoalServiceImpl(GoalRepository goalRepository, GoalMapper goalMapper) {
        this.goalRepository = goalRepository;
        this.goalMapper = goalMapper;
    }

    @Override
    public GoalBo save(GoalBo bo) {
        Goal entity = goalMapper.toEntity(bo);
        return goalMapper.toBo(goalRepository.save(entity));
    }

    @Override
    public GoalBo update(Long id, GoalBo bo) {
        goalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Goal not found"));
        Goal updated = goalMapper.toEntity(bo);
        updated.setId(id);
        return goalMapper.toBo(goalRepository.save(updated));
    }

    @Override
    public List<GoalBo> findAll() {
        return goalRepository.findAll().stream()
                .map(goalMapper::toBo)
                .collect(Collectors.toList());
    }

    @Override
    public GoalBo findById(Long id) {
        return goalRepository.findById(id)
                .map(goalMapper::toBo)
                .orElseThrow(() -> new RuntimeException("Goal not found"));
    }

    @Override
    public void delete(Long id) {
        goalRepository.deleteById(id);
    }

    @Override
    public List<GoalBo> findByAchieved(boolean achieved) {
        return goalRepository.findAllByAchieved(achieved).stream()
                .map(goalMapper::toBo)
                .collect(Collectors.toList());
    }
}
