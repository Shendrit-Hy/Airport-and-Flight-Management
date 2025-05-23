package com.mbi_re.airport_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/weather")


public class WeatherController {
    @Autowired
    private RestTemplate restTemplate;
    @GetMapping("/current")
    public ResponseEntity<String> getCurrentWeather() {
        String apiKey = "c8e04e837b134803a7c132638252305";
        String url = "https://api.weatherapi.com/v1/current.json?key=" + apiKey + "&q=Kosovo";

        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);

        return ResponseEntity.ok(response);
    }
}
