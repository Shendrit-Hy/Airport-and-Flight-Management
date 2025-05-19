package com.mbi_re.airport_management.controller;

import com.mbi_re.airport_management.dto.ParkingPriceDTO;
import com.mbi_re.airport_management.model.ParkingPrice;
import com.mbi_re.airport_management.service.ParkingPriceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/parking")
public class ParkingPriceController {

    private final ParkingPriceService service;

    public ParkingPriceController(ParkingPriceService service) {
        this.service = service;
    }

    @PostMapping("/calculate")
    public ResponseEntity<ParkingPrice> calculate(@RequestBody ParkingPriceDTO dto,
                                                  @RequestHeader("X-Tenant-ID") String tenantId) {
        dto.setTenantId(tenantId);
        ParkingPrice result = service.calculateAndSave(dto);
        return ResponseEntity.ok(result);
    }
}
