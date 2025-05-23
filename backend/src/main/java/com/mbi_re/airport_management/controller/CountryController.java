package com.mbi_re.airport_management.controller;

import com.mbi_re.airport_management.config.TenantContext;
import com.mbi_re.airport_management.dto.CountryDTO;
import com.mbi_re.airport_management.service.CountryService;
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
 * Controller for managing countries per tenant.
 */
@RestController
@RequestMapping("/api/countries")
@Tag(name = "Country Management", description = "Endpoints for managing countries")
public class CountryController {

    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    /**
     * Retrieve all countries for the specified tenant.
     * This endpoint requires the tenant ID via the "X-Tenant-ID" header.
     *
     * @param tenantId Tenant ID from the request header to identify tenant context
     * @return ResponseEntity containing the list of CountryDTO objects for the tenant
     */
    @Operation(
            summary = "Get all countries",
            description = "Public endpoint to retrieve all countries for the specified tenant."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of countries returned successfully"),
            @ApiResponse(responseCode = "403", description = "Invalid or missing tenant ID")
    })
    @GetMapping
    @Cacheable(value = "countries", key = "#tenantId")
    public ResponseEntity<List<CountryDTO>> getAllCountries(
            @RequestHeader("X-Tenant-ID")
            @Parameter(description = "Tenant ID from request header", required = true)
            String tenantId) {

        TenantUtil.validateTenant(tenantId);
        return ResponseEntity.ok(countryService.getAllCountries());
    }

    /**
     * Create a new country for the current tenant.
     * Access restricted to users with ADMIN role.
     *
     * @param dto CountryDTO containing details of the country to be created
     * @return ResponseEntity containing the created CountryDTO
     */
    @Operation(
            summary = "Create new country",
            description = "Admin-only endpoint to add a new country within the tenant context."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Country created successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied or missing tenant context")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @CacheEvict(value = "countries", key = "T(com.mbi_re.airport_management.config.TenantContext).getTenantId()")
    public ResponseEntity<CountryDTO> createCountry(
            @RequestBody
            @Parameter(description = "Country data to create", required = true)
            CountryDTO dto) {

        TenantUtil.validateTenantFromContext();
        return ResponseEntity.ok(countryService.createCountry(dto));
    }

    /**
     * Delete a country by its ID for the current tenant.
     * Access restricted to users with ADMIN role.
     *
     * @param id ID of the country to delete
     * @return ResponseEntity with HTTP status 204 No Content on successful deletion
     */
    @Operation(
            summary = "Delete a country",
            description = "Admin-only endpoint to delete a country by its ID within the tenant context."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Country deleted successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied or missing tenant context")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @CacheEvict(value = "countries", key = "T(com.mbi_re.airport_management.config.TenantContext).getTenantId()")
    public ResponseEntity<Void> deleteCountry(
            @PathVariable
            @Parameter(description = "Country ID to delete", required = true)
            Long id) {

        TenantUtil.validateTenantFromContext();
        countryService.deleteCountry(id);
        return ResponseEntity.noContent().build();
    }

}
