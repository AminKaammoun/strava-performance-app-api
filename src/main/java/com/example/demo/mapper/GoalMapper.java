package com.example.demo.mapper;

import com.example.demo.model.Goal;
import com.example.demo.model.GoalBo;
import com.example.demo.model.Race;
import com.example.demo.repository.RaceRepository;
import org.springframework.stereotype.Component;

@Component
public class GoalMapper {

    private final RaceRepository raceRepository;

    public GoalMapper(RaceRepository raceRepository) {
        this.raceRepository = raceRepository;
    }

    public GoalBo toBo(Goal goal) {
        GoalBo bo = new GoalBo();
        bo.setId(goal.getId());
        bo.setTitle(goal.getTitle());
        bo.setDescription(goal.getDescription());
        bo.setType(goal.getType());
        bo.setTargetValue(goal.getTargetValue());
        bo.setCurrentValue(goal.getCurrentValue());
        bo.setTargetDate(goal.getTargetDate());
        bo.setAchieved(goal.isAchieved());
        if (goal.getRelatedRace() != null) {
            bo.setRelatedRaceId(goal.getRelatedRace().getId());
            bo.setRelatedRaceName(goal.getRelatedRace().getName());
        }
        return bo;
    }

    public Goal toEntity(GoalBo bo) {
        Goal goal = new Goal();
        goal.setId(bo.getId());
        goal.setTitle(bo.getTitle());
        goal.setDescription(bo.getDescription());
        goal.setType(bo.getType());
        goal.setTargetValue(bo.getTargetValue());
        goal.setCurrentValue(bo.getCurrentValue());
        goal.setTargetDate(bo.getTargetDate());
        goal.setAchieved(bo.isAchieved());
        if (bo.getRelatedRaceId() != null) {
            Race race = raceRepository.findById(bo.getRelatedRaceId())
                    .orElseThrow(() -> new RuntimeException("Race not found: " + bo.getRelatedRaceId()));
            goal.setRelatedRace(race);
        }
        return goal;
    }
}
