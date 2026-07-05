package com.example.demo.service.Strava;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.demo.model.Strava.StravaActivity;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class StravaService {

    private final RestTemplate restTemplate;
    private final String clientId;
    private final String clientSecret;
    private final String refreshToken;
    private final String athleteId;

    public StravaService(
            @Value("${strava.client-id}") String clientId,
            @Value("${strava.client-secret}") String clientSecret,
            @Value("${strava.refresh-token}") String refreshToken,
            @Value("${strava.athlete-id}") String athleteId,
            RestTemplateBuilder restTemplateBuilder) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.refreshToken = refreshToken;
        this.athleteId = athleteId;
        this.restTemplate = restTemplateBuilder
                .setConnectTimeout(Duration.ofSeconds(10))
                .setReadTimeout(Duration.ofSeconds(10))
                .build();
    }

    public List<StravaActivity> getActivities() {
        String accessToken = getAccessToken();
        List<StravaActivity> activities = new ArrayList<>();

        for (int page = 1; page <= 3; page++) {
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(accessToken);
            headers.setAccept(List.of(MediaType.APPLICATION_JSON));
            HttpEntity<Void> entity = new HttpEntity<>(headers);

            String url = UriComponentsBuilder
                    .fromHttpUrl("https://www.strava.com/api/v3/athlete/activities")
                    .queryParam("per_page", 100)
                    .queryParam("page", page)
                    .build()
                    .toUriString();

            ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    new ParameterizedTypeReference<>() {
                    });

            List<Map<String, Object>> payload = response.getBody();
            if (payload == null || payload.isEmpty()) {
                break;
            }

            payload.stream()
                    .map(this::mapActivity)
                    .forEach(activities::add);
        }

        return activities;
    }

    private String getAccessToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("client_id", clientId);
        form.add("client_secret", clientSecret);
        form.add("refresh_token", refreshToken);
        form.add("grant_type", "refresh_token");

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(form, headers);
        Map<String, Object> tokenResponse = restTemplate.postForObject(
                "https://www.strava.com/api/v3/oauth/token",
                entity,
                Map.class);

        if (tokenResponse == null || tokenResponse.get("access_token") == null) {
            throw new IllegalStateException("Unable to obtain a Strava access token");
        }

        return String.valueOf(tokenResponse.get("access_token"));
    }

    @SuppressWarnings("unchecked")
    private StravaActivity mapActivity(Map<String, Object> raw) {
        StravaActivity activity = new StravaActivity();

        activity.setId(getLong(raw, "id"));
        activity.setResourceState(getInteger(raw, "resource_state"));
        activity.setExternalId(getString(raw, "external_id"));
        activity.setUploadId(getLong(raw, "upload_id"));

        activity.setName(getString(raw, "name"));
        activity.setDistance(getDouble(raw, "distance"));
        activity.setMovingTime(getLong(raw, "moving_time"));
        activity.setElapsedTime(getLong(raw, "elapsed_time"));
        activity.setTotalElevationGain(getDouble(raw, "total_elevation_gain"));
        activity.setElevHigh(getDouble(raw, "elev_high"));
        activity.setElevLow(getDouble(raw, "elev_low"));

        activity.setType(getString(raw, "type"));
        activity.setSportType(getString(raw, "sport_type"));

        activity.setStartDate(parseDate(getString(raw, "start_date")));
        activity.setStartDateLocal(parseDate(getString(raw, "start_date_local")));
        activity.setTimezone(getString(raw, "timezone"));
        activity.setUtcOffset(getDouble(raw, "utc_offset"));

        List<Object> startLatLng = (List<Object>) raw.get("start_latlng");
        activity.setStartLatitude(getLatLngValue(startLatLng, 0));
        activity.setStartLongitude(getLatLngValue(startLatLng, 1));

        List<Object> endLatLng = (List<Object>) raw.get("end_latlng");
        activity.setEndLatitude(getLatLngValue(endLatLng, 0));
        activity.setEndLongitude(getLatLngValue(endLatLng, 1));

        activity.setAchievementCount(getInteger(raw, "achievement_count"));
        activity.setKudosCount(getInteger(raw, "kudos_count"));
        activity.setCommentCount(getInteger(raw, "comment_count"));
        activity.setAthleteCount(getInteger(raw, "athlete_count"));
        activity.setPhotoCount(getInteger(raw, "photo_count"));
        activity.setTotalPhotoCount(getInteger(raw, "total_photo_count"));

        Map<String, Object> map = (Map<String, Object>) raw.get("map");
        if (map != null) {
            activity.setMapId(getString(map, "id"));
            activity.setMapSummaryPolyline(getString(map, "summary_polyline"));
        }

        activity.setTrainer(getBoolean(raw, "trainer"));
        activity.setCommute(getBoolean(raw, "commute"));
        activity.setManual(getBoolean(raw, "manual"));
        activity.setIsPrivate(getBoolean(raw, "private"));
        activity.setFlagged(getBoolean(raw, "flagged"));
        activity.setWorkoutType(getInteger(raw, "workout_type"));

        activity.setAverageSpeed(getDouble(raw, "average_speed"));
        activity.setMaxSpeed(getDouble(raw, "max_speed"));
        activity.setHasKudoed(getBoolean(raw, "has_kudoed"));
        activity.setHideFromHome(getBoolean(raw, "hide_from_home"));
        activity.setShoeId(getString(raw, "shoe_id"));

        activity.setKilojoules(getDouble(raw, "kilojoules"));
        activity.setAverageWatts(getDouble(raw, "average_watts"));
        activity.setDeviceWatts(getBoolean(raw, "device_watts"));
        activity.setMaxWatts(getInteger(raw, "max_watts"));
        activity.setWeightedAverageWatts(getInteger(raw, "weighted_average_watts"));

        activity.setHasHeartrate(getBoolean(raw, "has_heartrate"));
        activity.setAverageHeartrate(getDouble(raw, "average_heartrate"));
        activity.setMaxHeartrate(getDouble(raw, "max_heartrate"));

        activity.setPrCount(getInteger(raw, "pr_count"));

        activity.setAthleteId(athleteId);
        activity.setDescription(buildDescription(activity));
        return activity;
    }

    private LocalDateTime parseDate(String value) {
        if (value == null) {
            return null;
        }
        // Strava returns e.g. "2024-05-01T07:15:00Z"
        return LocalDateTime.parse(value.replace("Z", ""), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    private String buildDescription(StravaActivity activity) {
        String distance = activity.getDistance() == null ? "n/a"
                : String.format("%.2f km", activity.getDistance() / 1000.0);
        String time = activity.getMovingTime() == null ? "n/a" : String.format("%d min", activity.getMovingTime() / 60);
        return String.format("%s • %s • %s", activity.getType(), distance, time);
    }

    private String getString(Map<String, Object> map, String key) {
        Object value = map.get(key);
        return value == null ? null : String.valueOf(value);
    }

    private Long getLong(Map<String, Object> map, String key) {
        Object value = map.get(key);
        if (value instanceof Number number) {
            return number.longValue();
        }
        return null;
    }

    private Double getDouble(Map<String, Object> map, String key) {
        Object value = map.get(key);
        if (value instanceof Number number) {
            return number.doubleValue();
        }
        return null;
    }

    private Integer getInteger(Map<String, Object> map, String key) {
        Object value = map.get(key);
        if (value instanceof Number number) {
            return number.intValue();
        }
        return null;
    }

    private Boolean getBoolean(Map<String, Object> map, String key) {
        Object value = map.get(key);
        if (value instanceof Boolean bool) {
            return bool;
        }
        return null;
    }

    private Double getLatLngValue(List<Object> latLng, int index) {
        if (latLng == null || latLng.size() <= index) {
            return null;
        }
        Object value = latLng.get(index);
        if (value instanceof Number number) {
            return number.doubleValue();
        }
        return null;
    }
}
