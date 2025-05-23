package com.mbi_re.airport_management.controller;

import com.mbi_re.airport_management.config.TenantContext;
import com.mbi_re.airport_management.dto.TrendingPlaceDTO;
import com.mbi_re.airport_management.model.TrendingPlace;
import com.mbi_re.airport_management.service.TrendingPlaceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing trending places in the airport system.
 * Supports CRUD operations, with multi-tenant support via X-Tenant-ID header.
 */
@RestController
@RequestMapping("/api/trending-places")
@Tag(name = "Trending Places", description = "Endpoints for managing trending places")
public class TrendingPlaceController {

    private final TrendingPlaceService service;

    public TrendingPlaceController(TrendingPlaceService service) {
        this.service = service;
    }

    /**
     * Retrieve all trending places for the specified tenant.
     *
     * @param tenantId The tenant ID passed via the X-Tenant-ID header.
     * @return List of trending places.
     */
    @GetMapping
    @Operation(summary = "Get all trending places", description = "Fetch all trending places for the current tenant.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
            @ApiResponse(responseCode = "400", description = "Invalid tenant ID supplied"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public List<TrendingPlace> getTrendingPlaces(@RequestHeader("X-Tenant-ID") String tenantId) {
        TenantContext.setTenantId(tenantId);
        return service.getTrendingPlaces(tenantId);
    }

    /**
     * Create a new trending place. Admin-only access.
     *
     * @param dto      The trending place data transfer object.
     * @param tenantId The tenant ID from the header.
     * @return The created trending place entity.
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create a new trending place", description = "Create a trending place for the tenant (Admin only).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Trending place created successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public TrendingPlace createTrendingPlace(@RequestBody TrendingPlaceDTO dto,
                                             @RequestHeader("X-Tenant-ID") String tenantId) {
        TenantContext.setTenantId(tenantId);
        return service.createTrendingPlace(dto, tenantId);
    }

    /**
     * Delete a trending place by ID. Admin-only access.
     *
     * @param id       ID of the trending place to delete.
     * @param tenantId The tenant ID from the header.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete a trending place", description = "Delete a trending place by ID (Admin only).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Trending place deleted successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "404", description = "Trending place not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public void deleteTrendingPlace(@PathVariable Long id,
                                    @RequestHeader("X-Tenant-ID") String tenantId) {
        TenantContext.setTenantId(tenantId);
        service.deleteTrendingPlace(id, tenantId);
    }
}
