package com.mbi_re.airport_management.dto;

public class TerminalDTO {
    private Long id;
    private String name;
    private Long airportId;

    public TerminalDTO() {}

    public TerminalDTO(Long id, String name, Long airportId) {
        this.id = id;
        this.name = name;
        this.airportId = airportId;
    }

    // Getters dhe Setters

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

    public Long getAirportId() {
        return airportId;
    }

    public void setAirportId(Long airportId) {
        this.airportId = airportId;
    }
}
