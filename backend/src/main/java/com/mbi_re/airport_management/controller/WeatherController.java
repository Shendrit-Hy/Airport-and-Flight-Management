package com.mbi_re.airport_management.controller;

import com.mbi_re.airport_management.dto.WeatherDTO;
import com.mbi_re.airport_management.model.Weather;
import com.mbi_re.airport_management.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping("/api/weather")
    public WeatherDTO getWeather() {
        return weatherService.getWeather();
    }
}
