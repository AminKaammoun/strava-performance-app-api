package com.example.demo.mapper;

import com.example.demo.model.*;
import com.example.demo.repository.CityRepository;
import com.example.demo.repository.ShoeRepository;
import com.example.demo.repository.Strava.StravaActivityRepository;

import org.springframework.stereotype.Component;

@Component
public class RaceMapper {

    private final CityRepository cityRepository;
    private final ShoeRepository shoeRepository;
    private final StravaActivityRepository stravaActivityRepository;

    public RaceMapper(
            CityRepository cityRepository,
            ShoeRepository shoeRepository,
            StravaActivityRepository stravaActivityRepository) {
        this.cityRepository = cityRepository;
        this.shoeRepository = shoeRepository;
        this.stravaActivityRepository = stravaActivityRepository;
    }

    public RaceBo toBo(Race race) {
        RaceBo bo = new RaceBo();
        bo.setId(race.getId());
        bo.setName(race.getName());
        bo.setRaceDate(race.getRaceDate());
        bo.setDistanceKm(race.getDistanceKm());

        if (race.getCity() != null) {
            bo.setCityId(race.getCity().getId());
            bo.setCityName(race.getCity().getName());
            if (race.getCity().getCountry() != null) {
                bo.setCountryName(race.getCity().getCountry().getName());
            }
        }

        if (race.getShoe() != null) {
            bo.setShoeId(race.getShoe().getId());
            bo.setShoeName(race.getShoe().getName());
        }

        if (race.getStravaActivity() != null) {
            bo.setStravaActivityId(race.getStravaActivity().getId());
        }

        bo.setTargetTimeSeconds(race.getTargetTimeSeconds());
        bo.setActualTimeSeconds(race.getActualTimeSeconds());
        bo.setOverallPlacement(race.getOverallPlacement());
        bo.setCategoryPlacement(race.getCategoryPlacement());
        bo.setBibNumber(race.getBibNumber());
        bo.setNotes(race.getNotes());
        return bo;
    }

    public Race toEntity(RaceBo bo) {
        Race race = new Race();
        race.setId(bo.getId());
        race.setName(bo.getName());
        race.setRaceDate(bo.getRaceDate());
        race.setDistanceKm(bo.getDistanceKm());

        if (bo.getCityId() != null) {
            race.setCity(cityRepository.findById(bo.getCityId())
                    .orElseThrow(() -> new RuntimeException("City not found: " + bo.getCityId())));
        }

        if (bo.getShoeId() != null) {
            race.setShoe(shoeRepository.findById(bo.getShoeId())
                    .orElseThrow(() -> new RuntimeException("Shoe not found: " + bo.getShoeId())));
        }

        if (bo.getStravaActivityId() != null) {
            race.setStravaActivity(stravaActivityRepository.findById(bo.getStravaActivityId())
                    .orElseThrow(() -> new RuntimeException("Strava activity not found: " + bo.getStravaActivityId())));
        }

        race.setTargetTimeSeconds(bo.getTargetTimeSeconds());
        race.setActualTimeSeconds(bo.getActualTimeSeconds());
        race.setOverallPlacement(bo.getOverallPlacement());
        race.setCategoryPlacement(bo.getCategoryPlacement());
        race.setBibNumber(bo.getBibNumber());
        race.setNotes(bo.getNotes());
        return race;
    }
}
