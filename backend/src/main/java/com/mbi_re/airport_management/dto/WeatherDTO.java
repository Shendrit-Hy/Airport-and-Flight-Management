package com.mbi_re.airport_management.dto;

public class WeatherDTO {
    private String temperature;
    private String condition;
    private String runwayStatus;

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
