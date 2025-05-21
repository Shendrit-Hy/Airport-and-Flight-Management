package com.mbi_re.airport_management.controller;

import com.mbi_re.airport_management.dto.AirportDTO;
import com.mbi_re.airport_management.model.Airport;
import com.mbi_re.airport_management.service.AirportService;
import com.mbi_re.airport_management.utils.TenantUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/airports")
@Tag(name = "Airports", description = "Endpoints for managing airports per tenant")
public class AirportController {

    private final AirportService airportService;

    public AirportController(AirportService airportService) {
        this.airportService = airportService;
    }

    /**
     * Retrieves all airports for the authenticated tenant.
     *
     * @param tenantId tenant identifier from request header
     * @return list of airports
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    @Operation(summary = "Get all airports", description = "Returns all airports for the current tenant (ADMIN only)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved airports",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "Forbidden or tenant mismatch")
    })
    public ResponseEntity<List<Airport>> getAllAirports(
            @RequestHeader("X-Tenant-ID")
            @Parameter(description = "Tenant ID in header", required = true) String tenantId) {

        TenantUtil.validateTenant(tenantId); // JWT tenant validation
        return ResponseEntity.ok(airportService.getAllAirports(tenantId));
    }

    /**
     * Creates a new airport entry for the given tenant.
     * No login required; tenant validated from header.
     *
     * @param airportDTO airport data
     * @param tenantId   tenant identifier from header
     * @return created airport
     */
    @PostMapping
    @Operation(summary = "Create airport", description = "Creates a new airport for the given tenant (unauthenticated)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Airport created successfully",
                    content = @Content(schema = @Schema(implementation = Airport.class))),
            @ApiResponse(responseCode = "403", description = "Missing or invalid tenant context")
    })
    public ResponseEntity<Airport> createAirport(
            @RequestBody
            @Parameter(description = "Airport data") AirportDTO airportDTO,
            @RequestHeader("X-Tenant-ID")
            @Parameter(description = "Tenant ID in header", required = true) String tenantId) {

        TenantUtil.validateTenantFromContext(); // validate that the context is properly set
        Airport created = airportService.createAirport(airportDTO, tenantId);
        return ResponseEntity.ok(created);
    }

    /**
     * Deletes an airport by ID. Requires ADMIN access.
     *
     * @param id ID of the airport to delete
     * @return no content
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete airport", description = "Deletes an airport by ID (ADMIN only)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
    })
    public ResponseEntity<Void> deleteAirport(
            @PathVariable
            @Parameter(description = "Airport ID") Long id,
            @RequestHeader("X-Tenant-ID") String tenantId) {

        TenantUtil.validateTenant(tenantId);
        airportService.deleteAirport(id, tenantId );
        return ResponseEntity.noContent().build();
    }
}
