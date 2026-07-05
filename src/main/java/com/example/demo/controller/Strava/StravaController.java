package com.example.demo.controller.Strava;

import com.example.demo.model.Shoe;
import com.example.demo.model.Strava.StravaActivity;
import com.example.demo.model.Strava.SyncStatus;
import com.example.demo.service.Strava.StravaSyncService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/strava")
public class StravaController {

    private final StravaSyncService syncService;

    public StravaController(StravaSyncService syncService) {
        this.syncService = syncService;
    }

    // Frontend reads ALWAYS hit this — reads from our own DB, never Strava
    // directly.
    @GetMapping("/activities")
    public ResponseEntity<List<StravaActivity>> getActivities() {
        return ResponseEntity.ok(syncService.getStoredActivities());
    }

    @GetMapping("/sync-status")
    public ResponseEntity<SyncStatus> getSyncStatus() {
        return ResponseEntity.ok(syncService.getStatus());
    }

    // The ONLY endpoint that calls out to Strava.
    @PostMapping("/sync")
    public ResponseEntity<SyncStatus> sync() {
        return ResponseEntity.ok(syncService.syncNow());
    }

    // Frontend reads ALWAYS hit this — reads from our own DB, never Strava
    // directly.
    @GetMapping("/shoes")
    public ResponseEntity<List<Shoe>> getShoes() {
        return ResponseEntity.ok(syncService.getStoredShoes());
    }

    @GetMapping("/shoes/sync-status")
    public ResponseEntity<SyncStatus> getShoesSyncStatus() {
        return ResponseEntity.ok(syncService.getShoesStatus());
    }

    // The ONLY endpoint that calls out to Strava for gear.
    @PostMapping("/shoes/sync")
    public ResponseEntity<SyncStatus> syncShoes() {
        return ResponseEntity.ok(syncService.syncShoesNow());
    }
}
