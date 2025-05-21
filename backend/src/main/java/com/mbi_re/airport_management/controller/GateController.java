package com.mbi_re.airport_management.controller;

import com.mbi_re.airport_management.dto.GateDTO;
import com.mbi_re.airport_management.dto.GateResponseDTO;
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
    public ResponseEntity<List<GateResponseDTO>> getAllGates(@RequestHeader("X-Tenant-ID") String tenantId) {
        List<Gate> gates = gateService.getAllGates(tenantId);
        List<GateResponseDTO> response = gates.stream()
                .map(gateService::mapToResponseDTO)
                .toList();
        return ResponseEntity.ok(response);
    }


    @PostMapping
    public ResponseEntity<GateResponseDTO> createGate(
            @RequestBody GateDTO gateDTO,
            @RequestHeader("X-Tenant-ID") String tenantId) {

        Gate createdGate = gateService.createGate(gateDTO, tenantId);
        return ResponseEntity.ok(gateService.mapToResponseDTO(createdGate));
    }

}
