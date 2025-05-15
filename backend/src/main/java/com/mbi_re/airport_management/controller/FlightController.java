package com.mbi_re.airport_management.controller;

import com.mbi_re.airport_management.dto.FlightDTO;
import com.mbi_re.airport_management.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flights")
@RequiredArgsConstructor
public class FlightController {

    @Autowired
    private FlightService flightService;

    @GetMapping
    public List<FlightDTO> getFlights() {
        return flightService.getTodayAndUpcomingFlights();
    }

    @PostMapping
    public ResponseEntity<FlightDTO> addFlight(@RequestBody FlightDTO flightDTO) {
        FlightDTO saved = flightService.addFlight(flightDTO);
        return ResponseEntity.ok(saved);
    }
}
