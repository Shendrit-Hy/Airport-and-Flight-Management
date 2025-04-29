package com.mbi_re.airport_management.dto;

public class AirlineDTO {

    private Long id;
    private String name;
    private String iataCode;
    private String country;
    private String hubAirport;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIataCode() {
        return iataCode;
    }

    public void setIataCode(String iataCode) {
        this.iataCode = iataCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHubAirport() {
        return hubAirport;
    }

    public void setHubAirport(String hubAirport) {
        this.hubAirport = hubAirport;
    }
}

