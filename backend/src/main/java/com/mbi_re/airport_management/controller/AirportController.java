package com.mbi_re.airport_management.controller;

import com.mbi_re.airport_management.model.Airport;
import com.mbi_re.airport_management.service.AirportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/airports")
public class AirportController {

    @Autowired
    private AirportService airportService;

    @GetMapping("/{id}")
    public ResponseEntity<Airport> getAirportById(
            @PathVariable Long id,
            @RequestHeader("X-Tenant-ID") String tenantId) {

        Optional<Airport> airport = airportService.getAirportById(id, tenantId);
        return airport.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAirportById(
            @PathVariable Long id,
            @RequestHeader("X-Tenant-ID") String tenantId) {

        boolean deleted = airportService.deleteAirportById(id, tenantId);
        if (deleted) {
            return ResponseEntity.noContent().build(); // 204 No Content
        } else {
            return ResponseEntity.status(404).body("Airport not found or doesn't belong to tenant");
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateAirportById(
            @PathVariable Long id,
            @RequestHeader("X-Tenant-ID") String tenantId,
            @RequestBody Airport updatedAirport) {

        Optional<Airport> result = airportService.updateAirportById(id, tenantId, updatedAirport);
        return result.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404).body(null));
    }


}
