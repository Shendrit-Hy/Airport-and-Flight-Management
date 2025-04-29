package com.mbi_re.airport_management.model;

import jakarta.persistence.*;

@Entity
public class Weather {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String temperature;
    private String condition;
    private String runwayStatus;

    public Weather() {}

    public Weather(Long id, String temperature, String condition, String runwayStatus) {
        this.id = id;
        this.temperature = temperature;
        this.condition = condition;
        this.runwayStatus = runwayStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getRunwayStatus() {
        return runwayStatus;
    }

    public void setRunwayStatus(String runwayStatus) {
        this.runwayStatus = runwayStatus;
    }
}
