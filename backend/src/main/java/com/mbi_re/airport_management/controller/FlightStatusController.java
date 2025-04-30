package com.mbi_re.airport_management.controller;

import com.mbi_re.airport_management.dto.FlightStatusDTO;
import com.mbi_re.airport_management.service.FlightStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flight-status")
@PreAuthorize("hasAnyRole('ADMIN', 'STAFF', 'OPERATOR', 'USER')")
public class FlightStatusController {

    @Autowired
    private FlightStatusService service;

    @GetMapping("/{flightId}")
    public ResponseEntity<FlightStatusDTO> getFlightStatus(@PathVariable Long flightId) {
        return ResponseEntity.ok(service.getStatus(flightId));
    }

    @GetMapping("/live")
    public ResponseEntity<List<FlightStatusDTO>> getLiveFlights() {
        return ResponseEntity.ok(service.getLiveFlights());
    }
}
