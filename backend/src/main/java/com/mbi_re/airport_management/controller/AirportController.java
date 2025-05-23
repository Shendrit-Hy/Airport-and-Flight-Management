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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing airports within a multi-tenant environment.
 * Tenant context must be provided via the "X-Tenant-ID" header.
 * Administrative endpoints require ADMIN role.
 */
@RestController
@RequestMapping("/api/airports")
@Tag(name = "Airports", description = "Endpoints for managing airports per tenant")
public class AirportController {

    @Autowired
    private AirportService airportService;

    public AirportController(AirportService airportService) {
        this.airportService = airportService;
    }

    /**
     * Retrieves all airports for the authenticated tenant.
     * Access restricted to users with ADMIN role.
     *
     * @param tenantId Tenant identifier from the request header for tenant scoping.
     * @return HTTP 200 with list of airports in the response body.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    @Operation(
            summary = "Get all airports",
            description = "Returns all airports associated with the current tenant. Requires ADMIN role."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved airports",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Airport.class, type = "array"))
            ),
            @ApiResponse(responseCode = "403", description = "Forbidden or tenant mismatch", content = @Content)
    })
    public ResponseEntity<List<Airport>> getAllAirports(
            @RequestHeader("X-Tenant-ID")
            @Parameter(description = "Tenant ID in request header", required = true)
            String tenantId) {

        TenantUtil.validateTenant(tenantId);
        List<Airport> airports = airportService.getAllAirports(tenantId);
        return ResponseEntity.ok(airports);
    }

    /**
     * Creates a new airport entry for the specified tenant.
     * This endpoint does not require authentication, but tenant validation is enforced.
     *
     * @param airportDTO DTO containing airport data to be created.
     * @param tenantId   Tenant identifier from the request header.
     * @return HTTP 200 with the created airport in the response body.
     */
    @PostMapping
    @Operation(
            summary = "Create airport",
            description = "Creates a new airport for the given tenant. No authentication required but tenant validated."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Airport created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Airport.class))
            ),
            @ApiResponse(responseCode = "403", description = "Missing or invalid tenant context", content = @Content)
    })
    public ResponseEntity<Airport> createAirport(
            @RequestBody
            @Parameter(description = "Airport data to create", required = true,
                    content = @Content(schema = @Schema(implementation = AirportDTO.class)))
            AirportDTO airportDTO,
            @RequestHeader("X-Tenant-ID")
            @Parameter(description = "Tenant ID in request header", required = true)
            String tenantId) {

        TenantUtil.validateTenantFromContext();
        Airport createdAirport = airportService.createAirport(airportDTO, tenantId);
        return ResponseEntity.ok(createdAirport);
    }

    /**
     * Deletes an airport by its ID.
     * Requires ADMIN role.
     *
     * @param id       ID of the airport to delete.
     * @param tenantId Tenant identifier from the request header for tenant scoping.
     * @return HTTP 204 No Content if deletion is successful.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete airport",
            description = "Deletes an airport by ID. Requires ADMIN role."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden or tenant mismatch", content = @Content),
            @ApiResponse(responseCode = "404", description = "Airport not found", content = @Content)
    })
    public ResponseEntity<Void> deleteAirport(
            @PathVariable("id")
            @Parameter(description = "ID of the airport to delete", required = true)
            Long id,
            @RequestHeader("X-Tenant-ID")
            @Parameter(description = "Tenant ID in request header", required = true)
            String tenantId) {

        TenantUtil.validateTenant(tenantId);
        airportService.deleteAirport(id, tenantId);
        return ResponseEntity.noContent().build();
    }
}
