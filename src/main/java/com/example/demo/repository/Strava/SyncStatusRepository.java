package com.example.demo.repository.Strava;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Strava.SyncStatus;

public interface SyncStatusRepository extends JpaRepository<SyncStatus, String> {
}
