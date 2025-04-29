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
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("id", savedDTO.getId(), "message", "Maintenance record created successfully."));
    }

    @GetMapping
    public List<MaintenanceDTO> getMaintenance(
            @RequestParam(required = false) String airportCode,
            @RequestParam(required = false) String status) {

        if (airportCode != null && status != null) {
            return maintenanceService.getByAirportCodeAndStatus(airportCode, status);
        } else if (airportCode != null) {
            return maintenanceService.getByAirportCode(airportCode);
        } else if (status != null) {
            return maintenanceService.getByStatus(status);
        } else {
            return maintenanceService.getAll();
        }
    }
}
