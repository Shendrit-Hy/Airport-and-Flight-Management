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
     * Retrieves all cities for the tenant.
     *
     * @param tenantId Tenant ID provided in the request header for tenant context validation.
     * @return ResponseEntity containing the list of all CityDTOs.
     */
    @Operation(
            summary = "Get all cities",
            description = "Public endpoint to retrieve all cities available for the specified tenant."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of cities returned successfully"),
            @ApiResponse(responseCode = "403", description = "Invalid or missing tenant ID")
    })
    @GetMapping
    @Cacheable(value = "cities", key = "#tenantId + '_all'")
    public ResponseEntity<List<CityDTO>> getAllCities(
            @RequestHeader("X-Tenant-ID")
            @Parameter(description = "Tenant ID from request header", required = true)
            String tenantId) {

        TenantUtil.validateTenant(tenantId);
        return ResponseEntity.ok(cityService.getAllCities());
    }

    /**
     * Retrieves cities filtered by a specific country ID.
     *
     * @param tenantId  Tenant ID from the request header for tenant context validation.
     * @param countryId The ID of the country whose cities should be retrieved.
     * @return ResponseEntity containing the list of CityDTOs filtered by country.
     */
    @Operation(
            summary = "Get cities by country",
            description = "Public endpoint to retrieve all cities for a given country ID within the tenant context."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of cities filtered by country returned successfully"),
            @ApiResponse(responseCode = "403", description = "Invalid or missing tenant ID")
    })
    @GetMapping("/country/{countryId}")
    @Cacheable(value = "cities", key = "#tenantId + '_country_' + #countryId")
    public ResponseEntity<List<CityDTO>> getCitiesByCountry(
            @RequestHeader("X-Tenant-ID")
            @Parameter(description = "Tenant ID from request header", required = true)
            String tenantId,
            @PathVariable
            @Parameter(description = "Country ID to filter cities", required = true)
            Long countryId) {

        TenantUtil.validateTenant(tenantId);
        return ResponseEntity.ok(cityService.getCitiesByCountry(countryId));
    }

    /**
     * Creates a new city.
     * <p>
     * Requires the user to have ADMIN role.
     *
     * @param dto CityDTO object containing details for the city to be created.
     * @return ResponseEntity containing the created CityDTO.
     */
    @Operation(
            summary = "Create new city",
            description = "Admin-only endpoint to add a new city within the tenant's context."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "City created successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied or missing tenant context")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @CacheEvict(value = "cities", allEntries = true)
    public ResponseEntity<CityDTO> createCity(
            @RequestBody
            @Parameter(description = "City data to create", required = true)
            CityDTO dto) {

        TenantUtil.validateTenantFromContext();
        return ResponseEntity.ok(cityService.createCity(dto));
    }

    /**
     * Deletes a city by its ID.
     * <p>
     * Requires the user to have ADMIN role.
     *
     * @param id The ID of the city to delete.
     * @return ResponseEntity with HTTP status 204 No Content if deletion is successful.
     */
    @Operation(
            summary = "Delete a city",
            description = "Admin-only endpoint to delete a city by its ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "City deleted successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied or missing tenant context")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @CacheEvict(value = "cities", allEntries = true)
    public ResponseEntity<Void> deleteCity(
            @PathVariable
            @Parameter(description = "City ID to delete", required = true)
            Long id) {

        TenantUtil.validateTenantFromContext();
        cityService.deleteCity(id);
        return ResponseEntity.noContent().build();
    }
}
