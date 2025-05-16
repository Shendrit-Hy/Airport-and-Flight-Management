package com.mbi_re.airport_management.controller;

import com.mbi_re.airport_management.dto.AirportDTO;
import com.mbi_re.airport_management.model.Airport;
import com.mbi_re.airport_management.service.AirportService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/airports")
public class AirportController {

    private final AirportService airportService;

    public AirportController(AirportService airportService) {
        this.airportService = airportService;
    }

    private String extractTenantId(String host) {
        if (host == null || !host.contains(".")) return "default";
        return host.split("\\.")[0];
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<Airport>> getAllAirports(
            @RequestHeader("X-Tenant-ID") String tenantId) {
        return ResponseEntity.ok(airportService.getAllAirports(tenantId));
    }
//    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Airport> createAirport(
            @RequestBody AirportDTO airportDTO,
            @RequestHeader("X-Tenant-ID") String tenantId) {
        Airport created = airportService.createAirport(airportDTO, tenantId);
        return ResponseEntity.ok(created);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAirport(@PathVariable Long id) {
        airportService.deleteAirport(id);
        return ResponseEntity.noContent().build();
    }
}
