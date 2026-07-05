package com.example.demo.repository;

import com.example.demo.model.Goal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GoalRepository extends JpaRepository<Goal, Long> {
    List<Goal> findAllByAchieved(boolean achieved);
}
