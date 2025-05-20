package com.mbi_re.airport_management.controller;

import com.mbi_re.airport_management.config.TenantContext;
import com.mbi_re.airport_management.dto.PassengerDTO;
import com.mbi_re.airport_management.model.Passenger;
import com.mbi_re.airport_management.service.PassengerService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/passengers")
@CrossOrigin
public class PassengerController {

    private final PassengerService passengerService;

    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<Passenger> getAll(@RequestHeader("X-Tenant-ID") String tenantId) {
        return passengerService.getAllByTenantId(tenantId);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Passenger> update(
            @PathVariable Long id,
            @RequestBody PassengerDTO updated,
            @RequestHeader("X-Tenant-ID") String tenantId) {
        return ResponseEntity.ok(passengerService.update(id, updated, tenantId));
    }

    @PostMapping
    public ResponseEntity<PassengerDTO> savePassenger(
            @RequestBody PassengerDTO passengerDTO,
            @RequestHeader("X-Tenant-ID") String tenantId) {
        passengerDTO.setTenantId(tenantId);
        return ResponseEntity.ok(passengerService.savePassenger(passengerDTO));
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable Long id) {
        passengerService.deleteById(id);
    }


}
