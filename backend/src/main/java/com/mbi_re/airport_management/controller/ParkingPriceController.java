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

/**
 * REST controller for parking price calculations and management.
 *
 * <p>Supports both unauthenticated and authenticated calculation endpoints,
 * with tenant validation.</p>
 */
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
     *
     * <p>This endpoint is available for unauthenticated users who provide a tenant ID
     * in the "X-Tenant-ID" header. Tenant validation occurs via context set by TenantInterceptor.</p>
     *
     * @param dto parking pricing request data (e.g., duration, vehicle type)
     * @param tenantId tenant identifier extracted from the "X-Tenant-ID" HTTP header
     * @return ResponseEntity containing the calculated and saved ParkingPrice object
     */
    @PostMapping("/calculate")
    @Operation(
            summary = "Calculate parking price (unauthenticated)",
            description = "Calculate and store a parking price for a given tenant using X-Tenant-ID header."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Price calculated and saved successfully"),
            @ApiResponse(responseCode = "403", description = "Invalid or missing tenant ID")
    })
    public ResponseEntity<ParkingPrice> calculate(
            @RequestBody
            @Parameter(description = "Parking price calculation request data", required = true)
            ParkingPriceDTO dto,
            @RequestHeader("X-Tenant-ID")
            @Parameter(description = "Tenant ID from request header", required = true)
            String tenantId) {

        TenantUtil.validateTenantFromContext(); // Context should already have tenant set by interceptor
        dto.setTenantId(tenantId);
        ParkingPrice result = service.calculateAndSave(dto);
        return ResponseEntity.ok(result);
    }

    /**
     * Authenticated version of parking price calculation.
     *
     * <p>Only accessible to users with roles USER or ADMIN.
     * Tenant ID is extracted from JWT and injected into the request.</p>
     *
     * @param dto parking pricing request data
     * @param jwtTenantId tenant identifier extracted from JWT token
     * @return ResponseEntity containing the calculated and saved ParkingPrice object
     */
    @PostMapping("/secure/calculate")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(
            summary = "Calculate parking price (authenticated)",
            description = "Calculate and store a parking price for a logged-in user using JWT tenant ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Price calculated and saved successfully"),
            @ApiResponse(responseCode = "403", description = "Invalid or missing tenant ID or insufficient role")
    })
    public ResponseEntity<ParkingPrice> calculateSecure(
            @RequestBody
            @Parameter(description = "Parking price calculation request data", required = true)
            ParkingPriceDTO dto,
            @RequestAttribute("jwtTenantId")
            @Parameter(description = "Tenant ID from JWT", required = true)
            String jwtTenantId) {

        TenantUtil.validateTenant(jwtTenantId);
        dto.setTenantId(jwtTenantId);
        ParkingPrice result = service.calculateAndSave(dto);
        return ResponseEntity.ok(result);
    }
}
