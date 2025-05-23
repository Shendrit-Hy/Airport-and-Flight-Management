package com.mbi_re.airport_management.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Controller to fetch weather information from an external weather API.
 * Provides current weather data for a fixed location (Kosovo).
 */
@RestController
@RequestMapping("/api/weather")
@Tag(name = "Weather", description = "Endpoints to retrieve weather information")
public class WeatherController {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * Retrieves the current weather for Kosovo by calling an external weather API.
     *
     * @return A JSON string containing current weather data for Kosovo.
     */
    @GetMapping("/current")
    @Operation(
            summary = "Get current weather",
            description = "Fetches the current weather information for Kosovo from an external weather service."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved current weather",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(type = "string", example = "{\"location\": {...}, \"current\": {...}}"))),
            @ApiResponse(responseCode = "500", description = "Error fetching weather data", content = @Content)
    })
    public ResponseEntity<String> getCurrentWeather() {
        String apiKey = "c8e04e837b134803a7c132638252305";
        String url = "https://api.weatherapi.com/v1/current.json?key=" + apiKey + "&q=Kosovo";

        String response = restTemplate.getForObject(url, String.class);

        return ResponseEntity.ok(response);
    }
}
