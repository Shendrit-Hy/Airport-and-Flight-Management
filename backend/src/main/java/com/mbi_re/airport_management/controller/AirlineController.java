package com.mbi_re.airport_management.controller;

import com.mbi_re.airport_management.dto.AirlineDTO;
import com.mbi_re.airport_management.service.AirlineService;
import com.mbi_re.airport_management.utils.TenantUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/airlines")
@Tag(name = "Airlines", description = "Endpoints for managing airlines per tenant")
public class AirlineController {

    @Autowired
    private AirlineService airlineService;

    /**
     * Retrieves all airlines for the current tenant.
     *
     * @param tenantId tenant ID from request header
     * @return list of airline DTOs
     */
    @GetMapping
    @Operation(summary = "Get all airlines", description = "Returns all airlines for a tenant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of airlines",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "Missing tenant context or invalid tenant")
    })
    public List<AirlineDTO> getAllAirlines(
            @RequestHeader("X-Tenant-ID")
            @Parameter(description = "Tenant ID in request header", required = true) String tenantId) {

        TenantUtil.validateTenantFromContext();
        return airlineService.getAllAirlines(tenantId);
    }

    /**
     * Creates a new airline for the current tenant.
     * Requires ADMIN role.
     *
     * @param dto airline data
     * @param tenantId tenant ID from request header
     * @return created airline DTO
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @Operation(summary = "Create airline", description = "Creates a new airline (ADMIN only)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Airline created successfully"),
            @ApiResponse(responseCode = "403", description = "Forbidden or tenant mismatch")
    })
    public AirlineDTO createAirline(
            @RequestBody
            @Parameter(description = "Airline data") AirlineDTO dto,
            @RequestHeader("X-Tenant-ID")
            @Parameter(description = "Tenant ID in request header", required = true) String tenantId) {

        TenantUtil.validateTenant(tenantId);
        return airlineService.createAirline(dto, tenantId);
    }
}
