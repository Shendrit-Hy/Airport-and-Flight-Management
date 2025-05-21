package com.mbi_re.airport_management.controller;

import com.mbi_re.airport_management.dto.CityDTO;
import com.mbi_re.airport_management.service.CityService;
import com.mbi_re.airport_management.utils.TenantUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing cities within each tenant's domain.
 */
@RestController
@RequestMapping("/api/cities")
@Tag(name = "City Management", description = "Endpoints for managing cities")
public class CityController {

    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    /**
     * Get all cities for the tenant.
     *
     * @param tenantId Tenant ID from request header
     * @return List of cities
     */
    @Operation(summary = "Get all cities", description = "Public endpoint to retrieve all cities for a tenant.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of cities returned successfully"),
            @ApiResponse(responseCode = "403", description = "Invalid or missing tenant ID")
    })
    @GetMapping
    @Cacheable(value = "cities", key = "#tenantId + '_all'")
    public ResponseEntity<List<CityDTO>> getAllCities(
            @RequestHeader("X-Tenant-ID")
            @Parameter(description = "Tenant ID from header", required = true) String tenantId) {

        TenantUtil.validateTenant(tenantId);
        return ResponseEntity.ok(cityService.getAllCities());
    }

    /**
     * Get cities by country ID.
     *
     * @param tenantId  Tenant ID from header
     * @param countryId ID of the country
     * @return List of cities in the specified country
     */
    @Operation(summary = "Get cities by country", description = "Public endpoint to retrieve cities by country ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of cities by country returned successfully"),
            @ApiResponse(responseCode = "403", description = "Invalid or missing tenant ID")
    })
    @GetMapping("/country/{countryId}")
    @Cacheable(value = "cities", key = "#tenantId + '_country_' + #countryId")
    public ResponseEntity<List<CityDTO>> getCitiesByCountry(
            @RequestHeader("X-Tenant-ID")
            @Parameter(description = "Tenant ID from header", required = true) String tenantId,
            @PathVariable
            @Parameter(description = "Country ID", required = true) Long countryId) {

        TenantUtil.validateTenant(tenantId);
        return ResponseEntity.ok(cityService.getCitiesByCountry(countryId));
    }

    /**
     * Create a new city. Requires ADMIN role.
     *
     * @param dto City DTO to create
     * @return Created city
     */
    @Operation(summary = "Create new city", description = "Admin-only endpoint to add a new city.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "City created successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied or missing tenant context")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @CacheEvict(value = "cities", allEntries = true)
    public ResponseEntity<CityDTO> createCity(
            @RequestBody
            @Parameter(description = "City to create", required = true) CityDTO dto) {

        TenantUtil.validateTenantFromContext();
        return ResponseEntity.ok(cityService.createCity(dto));
    }

    /**
     * Delete a city by ID. Requires ADMIN role.
     *
     * @param id ID of the city to delete
     * @return 204 No Content if successful
     */
    @Operation(summary = "Delete a city", description = "Admin-only endpoint to delete a city by ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "City deleted successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied or missing tenant context")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @CacheEvict(value = "cities", allEntries = true)
    public ResponseEntity<Void> deleteCity(
            @PathVariable
            @Parameter(description = "City ID", required = true) Long id) {

        TenantUtil.validateTenantFromContext();
        cityService.deleteCity(id);
        return ResponseEntity.noContent().build();
    }
}
