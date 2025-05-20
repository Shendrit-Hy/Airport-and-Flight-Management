package com.mbi_re.airport_management.controller;

import com.mbi_re.airport_management.dto.GateDTO;
import com.mbi_re.airport_management.model.Gate;
import com.mbi_re.airport_management.service.GateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gates")
public class GateController {

    private final GateService gateService;

    public GateController(GateService gateService) {
        this.gateService = gateService;
    }

    @GetMapping
    public ResponseEntity<List<Gate>> getAllGates(@RequestHeader("X-Tenant-ID") String tenantId) {
        return ResponseEntity.ok(gateService.getAllGates(tenantId));
    }

    @PostMapping
    public ResponseEntity<Gate> createGate(@RequestBody GateDTO gateDTO, @RequestHeader("X-Tenant-ID") String tenantId) {
        Gate created = gateService.createGate(gateDTO, tenantId);
        return ResponseEntity.ok(created);
    }
}
