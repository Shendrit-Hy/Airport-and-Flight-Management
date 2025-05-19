package com.mbi_re.airport_management.controller;

import com.mbi_re.airport_management.dto.MaintenanceDTO;
import com.mbi_re.airport_management.service.MaintenanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/maintenance")
public class MaintenanceController {

    @Autowired
    private MaintenanceService maintenanceService;

    @PostMapping
    public ResponseEntity<?> createMaintenance(@RequestBody MaintenanceDTO maintenanceDTO) {
        MaintenanceDTO savedDTO = maintenanceService.create(maintenanceDTO);
        return ResponseEntity.status(201)
                .body(Map.of("id", savedDTO.getId(), "message", "Maintenance record created successfully."));
    }

    @GetMapping


    public ResponseEntity<List<MaintenanceDTO>> getMaintenance(
            @RequestParam(required = false) String airportCode,
            @RequestParam(required = false) String status) {

        List<MaintenanceDTO> result;
        if (airportCode != null && status != null) {
            result = maintenanceService.getByAirportCodeAndStatus(airportCode, status);
        } else if (airportCode != null) {
            result = maintenanceService.getByAirportCode(airportCode);
        } else if (status != null) {
            result = maintenanceService.getByStatus(status);
        } else {
            result = maintenanceService.getAll();
        }
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@RequestHeader("X-Tenant-ID") String tenantId,@PathVariable Long id) {
        maintenanceService.delete(id, tenantId);
        return ResponseEntity.noContent().build();
    }


}
