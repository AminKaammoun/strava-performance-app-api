package com.example.demo.repository;

import com.example.demo.model.Race;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RaceRepository extends JpaRepository<Race, Long> {
    List<Race> findAllByOrderByRaceDateDesc();
}
