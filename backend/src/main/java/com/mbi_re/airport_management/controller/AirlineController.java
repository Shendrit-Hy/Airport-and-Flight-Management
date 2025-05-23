package com.mbi_re.airport_management.controller;

import com.mbi_re.airport_management.dto.AirlineDTO;
import com.mbi_re.airport_management.service.AirlineService;
import com.mbi_re.airport_management.utils.TenantUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing airlines within a multi-tenant environment.
 * All endpoints require tenant context via the "X-Tenant-ID" header.
 * Administrative operations require 'ADMIN' role.
 */
@RestController
@RequestMapping("/api/airlines")
@Tag(name = "Airlines", description = "Endpoints for managing airlines per tenant")
public class AirlineController {

    @Autowired
    private AirlineService airlineService;

    /**
     * Retrieves all airlines for the specified tenant.
     *
     * @param tenantId Tenant ID provided in the request header to identify tenant context.
     * @return List of AirlineDTO representing airlines belonging to the tenant.
     */
    @GetMapping
    @Operation(
            summary = "Get all airlines",
            description = "Retrieve a list of all airlines associated with the current tenant."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of airlines",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AirlineDTO.class))),
            @ApiResponse(responseCode = "403", description = "Missing tenant context or invalid tenant",
                    content = @Content)
    })
    public List<AirlineDTO> getAllAirlines(
            @RequestHeader("X-Tenant-ID")
            @Parameter(description = "Tenant ID in request header", required = true)
            String tenantId) {

        TenantUtil.validateTenantFromContext();
        return airlineService.getAllAirlines(tenantId);
    }

    /**
     * Creates a new airline for the current tenant.
     * Only accessible to users with the ADMIN role.
     *
     * @param dto      AirlineDTO containing the data of the airline to be created.
     * @param tenantId Tenant ID from request header for tenant scoping.
     * @return AirlineDTO representing the newly created airline.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @Operation(
            summary = "Create airline",
            description = "Creates a new airline for the tenant. Requires ADMIN role."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Airline created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AirlineDTO.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden or tenant mismatch", content = @Content)
    })
    public AirlineDTO createAirline(
            @RequestBody
            @Parameter(description = "Airline data to create", required = true,
                    content = @Content(schema = @Schema(implementation = AirlineDTO.class)))
            AirlineDTO dto,
            @RequestHeader("X-Tenant-ID")
            @Parameter(description = "Tenant ID in request header", required = true)
            String tenantId) {

        TenantUtil.validateTenant(tenantId);
        return airlineService.createAirline(dto, tenantId);
    }

    /**
     * Deletes an existing airline by ID for the current tenant.
     * Only accessible to users with the ADMIN role.
     *
     * @param airlineId ID of the airline to be deleted.
     * @param tenantId  Tenant ID from request header for tenant scoping.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{airlineId}")
    @Operation(
            summary = "Delete airline",
            description = "Deletes an airline by ID for the tenant. Requires ADMIN role."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Airline deleted successfully", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden or tenant mismatch", content = @Content),
            @ApiResponse(responseCode = "404", description = "Airline not found", content = @Content)
    })
    public void deleteAirline(
            @PathVariable("airlineId")
            @Parameter(description = "ID of the airline to delete", required = true)
            Long airlineId,
            @RequestHeader("X-Tenant-ID")
            @Parameter(description = "Tenant ID in request header", required = true)
            String tenantId) {

        TenantUtil.validateTenant(tenantId);
        airlineService.deleteAirline(airlineId, tenantId);
    }
}
