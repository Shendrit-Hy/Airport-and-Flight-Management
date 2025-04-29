package com.mbi_re.airport_management.controller;

import com.mbi_re.airport_management.dto.WeatherDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherController {

    @GetMapping("/api/weather")
    public WeatherDTO getWeatherConditions() {
        WeatherDTO dto = new WeatherDTO();
        dto.setTemperature("18°C");
        dto.setCondition("Sunny");
        dto.setRunwayStatus("Runway clear and dry");
        return dto;
    }
}
