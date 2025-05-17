package com.mbi_re.airport_management.controller;

import com.mbi_re.airport_management.dto.FlightDTO;
import com.mbi_re.airport_management.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flights")
@RequiredArgsConstructor
public class FlightController {

    @Autowired
    private FlightService flightService;

    // Accessible by any authenticated user (user/admin)
    @GetMapping
    public List<FlightDTO> getFlights(@RequestHeader("X-Tenant-ID") String tenantId) {
        return flightService.getTodayAndUpcomingFlights(tenantId);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public List<FlightDTO> getAllFlights(@RequestHeader("X-Tenant-ID") String tenantId) {
        return flightService.getAllFlights(tenantId);
    }

    // Only ADMIN can add flights
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<FlightDTO> addFlight(
            @RequestHeader("X-Tenant-ID") String tenantId,
            @RequestBody FlightDTO flightDTO) {
        flightDTO.setTenantId(tenantId);
        FlightDTO saved = flightService.addFlight(flightDTO);
        return ResponseEntity.ok(saved);
    }

    // Only ADMIN can delete flights
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFlight(
            @RequestHeader("X-Tenant-ID") String tenantId,
            @PathVariable Long id) {
        flightService.deleteFlight(id, tenantId);
        return ResponseEntity.noContent().build();
    }
}
