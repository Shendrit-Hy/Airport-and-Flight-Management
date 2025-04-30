package com.mbi_re.airport_management.controller;

import com.mbi_re.airport_management.dto.FlightDTO;
import com.mbi_re.airport_management.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flights")
@PreAuthorize("hasRole('ADMIN') or hasRole('STAFF') or hasRole('OPERATOR')")
public class FlightController {

    @Autowired
    private FlightService flightService;

    @PostMapping
    public ResponseEntity<FlightDTO> createFlight(@RequestBody FlightDTO dto) {
        return ResponseEntity.ok(flightService.createFlight(dto));
    }

    @GetMapping
    public ResponseEntity<List<FlightDTO>> getAllFlights() {
        return ResponseEntity.ok(flightService.getAllFlights());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlightDTO> getFlightById(@PathVariable Long id) {
        return ResponseEntity.ok(flightService.getFlightById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FlightDTO> updateFlight(@PathVariable Long id, @RequestBody FlightDTO dto) {
        return ResponseEntity.ok(flightService.updateFlight(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFlight(@PathVariable Long id) {
        flightService.deleteFlight(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/status/{flightId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF', 'OPERATOR', 'USER')")
    public ResponseEntity<String> getFlightStatus(@PathVariable Long flightId) {
        return ResponseEntity.ok(flightService.getFlightStatus(flightId));
    }

    @GetMapping("/live")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF', 'OPERATOR', 'USER')")
    public ResponseEntity<List<FlightDTO>> getLiveFlights() {
        return ResponseEntity.ok(flightService.getLiveFlights());
    }
}
