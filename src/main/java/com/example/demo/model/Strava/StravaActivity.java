package com.example.demo.model.Strava;

import com.example.demo.model.Shoe;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;

@Entity
public class StravaActivity {

    @Id
    private Long id; // Strava's own activity id — used as-is so saving re-uses the row (upsert)

    private Integer resourceState;
    private String externalId;
    private Long uploadId;

    private String name;
    private Double distance;
    private Long movingTime;
    private Long elapsedTime;
    private Double totalElevationGain;
    private Double elevHigh;
    private Double elevLow;

    private String type;
    private String sportType;

    private LocalDateTime startDate;
    private LocalDateTime startDateLocal;
    private String timezone;
    private Double utcOffset;

    private Double startLatitude;
    private Double startLongitude;
    private Double endLatitude;
    private Double endLongitude;

    private Integer achievementCount;
    private Integer kudosCount;
    private Integer commentCount;
    private Integer athleteCount;
    private Integer photoCount;
    private Integer totalPhotoCount;

    private String mapId;

    @Column(columnDefinition = "TEXT")
    private String mapSummaryPolyline;

    private Boolean trainer;
    private Boolean commute;
    private Boolean manual;
    private Boolean isPrivate;
    private Boolean flagged;
    private Integer workoutType;

    private Double averageSpeed;
    private Double maxSpeed;
    private Double averageCadence;
    private Double averageTemp;
    private Boolean hasKudoed;
    private Boolean hideFromHome;

    // Strava's "gear_id" — id of the bike or shoe used. Kept as a plain column
    // (not a hard FK) so activity sync never fails or blocks on shoe sync order.
    @Column(name = "gear_id")
    private String gearId;

    // Read-only navigation to our own Shoe table via gearId. Populated by
    // Hibernate on read only — never written to directly (insertable/updatable
    // = false), and NO_CONSTRAINT so there's no DB-level FK to worry about if
    // the gear happens to be a bike (not stored) or hasn't been synced yet.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gear_id", referencedColumnName = "id", insertable = false, updatable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    @JsonIgnore
    private Shoe shoe;

    private Double kilojoules;
    private Double averageWatts;
    private Boolean deviceWatts;
    private Integer maxWatts;
    private Integer weightedAverageWatts;

    private Boolean hasHeartrate;
    private Double averageHeartrate;
    private Double maxHeartrate;

    private Integer prCount;
    private Double sufferScore;
    private Boolean fromAcceptedTag;

    // Detail-only fields — Strava never returns these on the activity LIST
    // endpoint we sync from, only on a per-activity GET. We're not making
    // that extra call per activity (see StravaService), so these are always
    // null. Kept as columns anyway so the "all columns" shape is complete and
    // ready to populate later without another migration.
    private Double calories;
    private String deviceName;
    private String embedToken;

    private String athleteId; // our configured athlete, not Strava's nested athlete object
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getResourceState() {
        return resourceState;
    }

    public void setResourceState(Integer resourceState) {
        this.resourceState = resourceState;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public Long getUploadId() {
        return uploadId;
    }

    public void setUploadId(Long uploadId) {
        this.uploadId = uploadId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Long getMovingTime() {
        return movingTime;
    }

    public void setMovingTime(Long movingTime) {
        this.movingTime = movingTime;
    }

    public Long getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(Long elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public Double getTotalElevationGain() {
        return totalElevationGain;
    }

    public void setTotalElevationGain(Double totalElevationGain) {
        this.totalElevationGain = totalElevationGain;
    }

    public Double getElevHigh() {
        return elevHigh;
    }

    public void setElevHigh(Double elevHigh) {
        this.elevHigh = elevHigh;
    }

    public Double getElevLow() {
        return elevLow;
    }

    public void setElevLow(Double elevLow) {
        this.elevLow = elevLow;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSportType() {
        return sportType;
    }

    public void setSportType(String sportType) {
        this.sportType = sportType;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getStartDateLocal() {
        return startDateLocal;
    }

    public void setStartDateLocal(LocalDateTime startDateLocal) {
        this.startDateLocal = startDateLocal;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public Double getUtcOffset() {
        return utcOffset;
    }

    public void setUtcOffset(Double utcOffset) {
        this.utcOffset = utcOffset;
    }

    public Double getStartLatitude() {
        return startLatitude;
    }

    public void setStartLatitude(Double startLatitude) {
        this.startLatitude = startLatitude;
    }

    public Double getStartLongitude() {
        return startLongitude;
    }

    public void setStartLongitude(Double startLongitude) {
        this.startLongitude = startLongitude;
    }

    public Double getEndLatitude() {
        return endLatitude;
    }

    public void setEndLatitude(Double endLatitude) {
        this.endLatitude = endLatitude;
    }

    public Double getEndLongitude() {
        return endLongitude;
    }

    public void setEndLongitude(Double endLongitude) {
        this.endLongitude = endLongitude;
    }

    public Integer getAchievementCount() {
        return achievementCount;
    }

    public void setAchievementCount(Integer achievementCount) {
        this.achievementCount = achievementCount;
    }

    public Integer getKudosCount() {
        return kudosCount;
    }

    public void setKudosCount(Integer kudosCount) {
        this.kudosCount = kudosCount;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public Integer getAthleteCount() {
        return athleteCount;
    }

    public void setAthleteCount(Integer athleteCount) {
        this.athleteCount = athleteCount;
    }

    public Integer getPhotoCount() {
        return photoCount;
    }

    public void setPhotoCount(Integer photoCount) {
        this.photoCount = photoCount;
    }

    public Integer getTotalPhotoCount() {
        return totalPhotoCount;
    }

    public void setTotalPhotoCount(Integer totalPhotoCount) {
        this.totalPhotoCount = totalPhotoCount;
    }

    public String getMapId() {
        return mapId;
    }

    public void setMapId(String mapId) {
        this.mapId = mapId;
    }

    public String getMapSummaryPolyline() {
        return mapSummaryPolyline;
    }

    public void setMapSummaryPolyline(String mapSummaryPolyline) {
        this.mapSummaryPolyline = mapSummaryPolyline;
    }

    public Boolean getTrainer() {
        return trainer;
    }

    public void setTrainer(Boolean trainer) {
        this.trainer = trainer;
    }

    public Boolean getCommute() {
        return commute;
    }

    public void setCommute(Boolean commute) {
        this.commute = commute;
    }

    public Boolean getManual() {
        return manual;
    }

    public void setManual(Boolean manual) {
        this.manual = manual;
    }

    public Boolean getIsPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(Boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public Boolean getFlagged() {
        return flagged;
    }

    public void setFlagged(Boolean flagged) {
        this.flagged = flagged;
    }

    public Integer getWorkoutType() {
        return workoutType;
    }

    public void setWorkoutType(Integer workoutType) {
        this.workoutType = workoutType;
    }

    public Double getAverageSpeed() {
        return averageSpeed;
    }

    public void setAverageSpeed(Double averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

    public Double getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(Double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public Double getAverageCadence() {
        return averageCadence;
    }

    public void setAverageCadence(Double averageCadence) {
        this.averageCadence = averageCadence;
    }

    public Double getAverageTemp() {
        return averageTemp;
    }

    public void setAverageTemp(Double averageTemp) {
        this.averageTemp = averageTemp;
    }

    public Boolean getHasKudoed() {
        return hasKudoed;
    }

    public void setHasKudoed(Boolean hasKudoed) {
        this.hasKudoed = hasKudoed;
    }

    public Boolean getHideFromHome() {
        return hideFromHome;
    }

    public void setHideFromHome(Boolean hideFromHome) {
        this.hideFromHome = hideFromHome;
    }

    public String getGearId() {
        return gearId;
    }

    public void setGearId(String gearId) {
        this.gearId = gearId;
    }

    public Shoe getShoe() {
        return shoe;
    }

    public void setShoe(Shoe shoe) {
        this.shoe = shoe;
    }

    public Double getKilojoules() {
        return kilojoules;
    }

    public void setKilojoules(Double kilojoules) {
        this.kilojoules = kilojoules;
    }

    public Double getAverageWatts() {
        return averageWatts;
    }

    public void setAverageWatts(Double averageWatts) {
        this.averageWatts = averageWatts;
    }

    public Boolean getDeviceWatts() {
        return deviceWatts;
    }

    public void setDeviceWatts(Boolean deviceWatts) {
        this.deviceWatts = deviceWatts;
    }

    public Integer getMaxWatts() {
        return maxWatts;
    }

    public void setMaxWatts(Integer maxWatts) {
        this.maxWatts = maxWatts;
    }

    public Integer getWeightedAverageWatts() {
        return weightedAverageWatts;
    }

    public void setWeightedAverageWatts(Integer weightedAverageWatts) {
        this.weightedAverageWatts = weightedAverageWatts;
    }

    public Boolean getHasHeartrate() {
        return hasHeartrate;
    }

    public void setHasHeartrate(Boolean hasHeartrate) {
        this.hasHeartrate = hasHeartrate;
    }

    public Double getAverageHeartrate() {
        return averageHeartrate;
    }

    public void setAverageHeartrate(Double averageHeartrate) {
        this.averageHeartrate = averageHeartrate;
    }

    public Double getMaxHeartrate() {
        return maxHeartrate;
    }

    public void setMaxHeartrate(Double maxHeartrate) {
        this.maxHeartrate = maxHeartrate;
    }

    public Integer getPrCount() {
        return prCount;
    }

    public void setPrCount(Integer prCount) {
        this.prCount = prCount;
    }

    public Double getSufferScore() {
        return sufferScore;
    }

    public void setSufferScore(Double sufferScore) {
        this.sufferScore = sufferScore;
    }

    public Boolean getFromAcceptedTag() {
        return fromAcceptedTag;
    }

    public void setFromAcceptedTag(Boolean fromAcceptedTag) {
        this.fromAcceptedTag = fromAcceptedTag;
    }

    public Double getCalories() {
        return calories;
    }

    public void setCalories(Double calories) {
        this.calories = calories;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getEmbedToken() {
        return embedToken;
    }

    public void setEmbedToken(String embedToken) {
        this.embedToken = embedToken;
    }

    public String getAthleteId() {
        return athleteId;
    }

    public void setAthleteId(String athleteId) {
        this.athleteId = athleteId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
