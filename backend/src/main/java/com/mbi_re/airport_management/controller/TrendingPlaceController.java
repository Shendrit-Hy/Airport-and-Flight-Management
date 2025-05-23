package com.mbi_re.airport_management.controller;

import com.mbi_re.airport_management.config.TenantContext;
import com.mbi_re.airport_management.dto.TrendingPlaceDTO;
import com.mbi_re.airport_management.model.TrendingPlace;
import com.mbi_re.airport_management.service.TrendingPlaceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing trending places in the airport system.
 * <p>
 * Supports CRUD operations with multi-tenant support enforced
 * via the {@code X-Tenant-ID} request header.
 * </p>
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
     * Retrieves all trending places associated with the specified tenant.
     * <p>
     * The tenant is identified via the {@code X-Tenant-ID} header.
     * Tenant context is set before fetching the data to ensure multi-tenant isolation.
     * </p>
     *
     * @param tenantId the tenant ID from the {@code X-Tenant-ID} header
     * @return a list of {@link TrendingPlace} entities belonging to the tenant
     */
    @GetMapping
    @Operation(
            summary = "Get all trending places",
            description = "Fetch all trending places for the current tenant."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
            @ApiResponse(responseCode = "400", description = "Invalid tenant ID supplied"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public List<TrendingPlace> getTrendingPlaces(
            @RequestHeader("X-Tenant-ID")
            @Parameter(description = "Tenant ID header for multi-tenant isolation", required = true, example = "tenant123")
            String tenantId) {

        TenantContext.setTenantId(tenantId);
        return service.getTrendingPlaces(tenantId);
    }

    /**
     * Creates a new trending place for the specified tenant.
     * <p>
     * Only users with the ADMIN role can access this endpoint.
     * Tenant context is set from the {@code X-Tenant-ID} header before creation.
     * </p>
     *
     * @param dto      the trending place data transfer object containing creation details
     * @param tenantId the tenant ID from the {@code X-Tenant-ID} header
     * @return the created {@link TrendingPlace} entity
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Create a new trending place",
            description = "Create a trending place for the tenant (Admin only).",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Trending place created successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public TrendingPlace createTrendingPlace(
            @RequestBody
            @Parameter(description = "Trending place data to create", required = true)
            TrendingPlaceDTO dto,

            @RequestHeader("X-Tenant-ID")
            @Parameter(description = "Tenant ID header for multi-tenant isolation", required = true, example = "tenant123")
            String tenantId) {

        TenantContext.setTenantId(tenantId);
        return service.createTrendingPlace(dto, tenantId);
    }

    /**
     * Deletes a trending place by its ID for the specified tenant.
     * <p>
     * Only users with the ADMIN role are authorized to perform this operation.
     * Tenant context is set from the {@code X-Tenant-ID} header before deletion.
     * </p>
     *
     * @param id       the ID of the trending place to delete
     * @param tenantId the tenant ID from the {@code X-Tenant-ID} header
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Delete a trending place",
            description = "Delete a trending place by ID (Admin only).",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Trending place deleted successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "404", description = "Trending place not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public void deleteTrendingPlace(
            @PathVariable
            @Parameter(description = "ID of the trending place to delete", required = true, example = "123")
            Long id,

            @RequestHeader("X-Tenant-ID")
            @Parameter(description = "Tenant ID header for multi-tenant isolation", required = true, example = "tenant123")
            String tenantId) {

        TenantContext.setTenantId(tenantId);
        service.deleteTrendingPlace(id, tenantId);
    }
}
