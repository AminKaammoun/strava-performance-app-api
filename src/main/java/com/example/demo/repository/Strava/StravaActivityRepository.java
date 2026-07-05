package com.example.demo.repository.Strava;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Strava.StravaActivity;

import java.util.List;

public interface StravaActivityRepository extends JpaRepository<StravaActivity, Long> {
    List<StravaActivity> findAllByOrderByStartDateLocalDesc();
}
