package com.example.demo.service.Strava;

import com.example.demo.model.Shoe;
import com.example.demo.model.Strava.StravaActivity;
import com.example.demo.model.Strava.SyncStatus;
import com.example.demo.repository.ShoeRepository;
import com.example.demo.repository.Strava.StravaActivityRepository;
import com.example.demo.repository.Strava.SyncStatusRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StravaSyncService {

    private static final String SOURCE = "strava";
    private static final String SHOES_SOURCE = "strava-shoes";

    private final StravaService stravaService; // talks to the external Strava API
    private final StravaActivityRepository activityRepository;
    private final ShoeRepository shoeRepository;
    private final SyncStatusRepository syncStatusRepository;

    public StravaSyncService(
            StravaService stravaService,
            StravaActivityRepository activityRepository,
            ShoeRepository shoeRepository,
            SyncStatusRepository syncStatusRepository) {
        this.stravaService = stravaService;
        this.activityRepository = activityRepository;
        this.shoeRepository = shoeRepository;
        this.syncStatusRepository = syncStatusRepository;
    }

    /**
     * The ONLY method in the app that calls out to the external Strava API.
     * Fetches activities and upserts them into the database, then stamps the sync
     * time.
     */
    @Transactional
    public SyncStatus syncNow() {
        List<StravaActivity> fetched = stravaService.getActivities();

        // save() on an entity that already has an @Id updates that row instead of
        // duplicating it —
        // this is what makes it an upsert, since each StravaActivity's id IS Strava's
        // own activity id.
        activityRepository.saveAll(fetched);

        SyncStatus status = syncStatusRepository.findById(SOURCE).orElseGet(SyncStatus::new);
        status.setSource(SOURCE);
        status.setLastSyncedAt(LocalDateTime.now());
        return syncStatusRepository.save(status);
    }

    public SyncStatus getStatus() {
        return syncStatusRepository.findById(SOURCE).orElse(null);
    }

    /** Reads ONLY from the database — never touches the external API. */
    public List<StravaActivity> getStoredActivities() {
        return activityRepository.findAllByOrderByStartDateLocalDesc();
    }

    /**
     * Fetches the athlete's shoes from Strava (bikes are dropped in
     * StravaService.getShoes()) and upserts them, keyed by Strava's gear id.
     * Manual/local fields (brand, type, purchaseDate, retired, notes) on
     * existing rows are preserved — only the Strava-owned fields are updated.
     */
    @Transactional
    public SyncStatus syncShoesNow() {
        List<Shoe> fetched = stravaService.getShoes();

        for (Shoe incoming : fetched) {
            Shoe existing = shoeRepository.findById(incoming.getId()).orElse(null);
            if (existing != null) {
                existing.setName(incoming.getName());
                existing.setPrimary(incoming.getPrimary());
                existing.setResourceState(incoming.getResourceState());
                existing.setDistanceMeters(incoming.getDistanceMeters());
                shoeRepository.save(existing);
            } else {
                shoeRepository.save(incoming);
            }
        }

        SyncStatus status = syncStatusRepository.findById(SHOES_SOURCE).orElseGet(SyncStatus::new);
        status.setSource(SHOES_SOURCE);
        status.setLastSyncedAt(LocalDateTime.now());
        return syncStatusRepository.save(status);
    }

    public SyncStatus getShoesStatus() {
        return syncStatusRepository.findById(SHOES_SOURCE).orElse(null);
    }

    /** Reads ONLY from the database — never touches the external API. */
    public List<Shoe> getStoredShoes() {
        return shoeRepository.findAll();
    }
}
