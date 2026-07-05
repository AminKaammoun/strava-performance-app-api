package com.example.demo.service;

import com.example.demo.model.GoalBo;

import java.util.List;

public interface GoalService extends GenericCrudService<GoalBo, Long> {
    List<GoalBo> findByAchieved(boolean achieved);
}
