package com.mbi_re.airport_management.controller;

import com.mbi_re.airport_management.dto.ParkingPriceDTO;
import com.mbi_re.airport_management.model.ParkingPrice;
import com.mbi_re.airport_management.service.ParkingPriceService;
import com.mbi_re.airport_management.utils.TenantUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/parking")
@Tag(name = "Parking Pricing", description = "Endpoints for calculating and managing parking prices")
public class ParkingPriceController {

    private final ParkingPriceService service;

    public ParkingPriceController(ParkingPriceService service) {
        this.service = service;
    }

    /**
     * Calculate and persist parking price based on provided data.
     * This endpoint is available to unauthenticated users using the X-Tenant-ID header.
     *
     * @param dto      parking pricing request data
     * @param tenantId tenant identifier from header (used before login)
     * @return calculated and saved ParkingPrice object
     */
    @PostMapping("/calculate")
    @Operation(
            summary = "Calculate parking price (unauthenticated)",
            description = "Calculate and store a parking price for a given tenant using X-Tenant-ID header"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Price calculated and saved successfully"),
            @ApiResponse(responseCode = "403", description = "Invalid or missing tenant ID")
    })
    public ResponseEntity<ParkingPrice> calculate(
            @RequestBody ParkingPriceDTO dto,
            @RequestHeader("X-Tenant-ID") @Parameter(description = "Tenant ID from request header") String tenantId) {
        TenantUtil.validateTenantFromContext(); // Context set by TenantInterceptor
        dto.setTenantId(tenantId);
        ParkingPrice result = service.calculateAndSave(dto);
        return ResponseEntity.ok(result);
    }

    /**
     * Authenticated version of parking price calculation.
     * Only accessible to users with USER or ADMIN role.
     *
     * @param dto          parking pricing request data
     * @param jwtTenantId  tenant identifier extracted from JWT and injected into request
     * @return calculated and saved ParkingPrice object
     */
    @PostMapping("/secure/calculate")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(
            summary = "Calculate parking price (authenticated)",
            description = "Calculate and store a parking price for a logged-in user using JWT tenant ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Price calculated and saved successfully"),
            @ApiResponse(responseCode = "403", description = "Invalid or missing tenant ID or role")
    })
    public ResponseEntity<ParkingPrice> calculateSecure(
            @RequestBody ParkingPriceDTO dto,
            @RequestAttribute("jwtTenantId") @Parameter(description = "Tenant ID from JWT") String jwtTenantId) {
        TenantUtil.validateTenant(jwtTenantId);
        dto.setTenantId(jwtTenantId);
        ParkingPrice result = service.calculateAndSave(dto);
        return ResponseEntity.ok(result);
    }
}
