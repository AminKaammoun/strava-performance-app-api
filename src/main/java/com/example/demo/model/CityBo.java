package com.example.demo.model;

public class CityBo {
    private Long id;
    private String name;
    private Long countryId;
    private String countryName; // convenience read-only field, populated by the mapper

    public CityBo() {}

    public CityBo(Long id, String name, Long countryId, String countryName) {
        this.id = id;
        this.name = name;
        this.countryId = countryId;
        this.countryName = countryName;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Long getCountryId() { return countryId; }
    public void setCountryId(Long countryId) { this.countryId = countryId; }

    public String getCountryName() { return countryName; }
    public void setCountryName(String countryName) { this.countryName = countryName; }
}
