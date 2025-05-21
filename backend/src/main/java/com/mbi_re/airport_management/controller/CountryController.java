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
     * Get all countries for the current tenant.
     * This is a public endpoint that requires tenant ID via header.
     *
     * @param tenantId Tenant ID from X-Tenant-ID header
     * @return List of countries
     */
    @Operation(summary = "Get all countries", description = "Public endpoint to retrieve all countries for a tenant.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of countries returned successfully"),
            @ApiResponse(responseCode = "403", description = "Invalid or missing tenant ID")
    })
    @GetMapping
    public List<CountryDTO> getAllCountries() {
        return countryService.getAllCountries();
    @Cacheable(value = "countries", key = "#tenantId")
    public ResponseEntity<List<CountryDTO>> getAllCountries(
            @RequestHeader("X-Tenant-ID")
            @Parameter(description = "Tenant ID from header", required = true) String tenantId) {

        TenantUtil.validateTenant(tenantId);
        return ResponseEntity.ok(countryService.getAllCountries());
    }

    /**
     * Create a new country. Requires ADMIN role.
     *
     * @param dto Country to be created
     * @return Created country
     */
    @Operation(summary = "Create new country", description = "Admin-only endpoint to add a new country.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Country created successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied or missing tenant context")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @CacheEvict(value = "countries", key = "T(com.mbi_re.airport_management.config.TenantContext).getTenantId()")
    public ResponseEntity<CountryDTO> createCountry(
            @RequestBody
            @Parameter(description = "Country to create", required = true) CountryDTO dto) {

        TenantUtil.validateTenantFromContext();
        return ResponseEntity.ok(countryService.createCountry(dto));
    }

    /**
     * Delete a country by its code. Requires ADMIN role.
     *
     * @param code Country code
     * @return 204 No Content on success
     */
    @Operation(summary = "Delete a country", description = "Admin-only endpoint to delete a country by code.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Country deleted successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied or missing tenant context")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{code}")
    @CacheEvict(value = "countries", key = "T(com.mbi_re.airport_management.config.TenantContext).getTenantId()")
    public ResponseEntity<Void> deleteCountry(
            @PathVariable
            @Parameter(description = "Country code", required = true) String code) {

        TenantUtil.validateTenantFromContext();
        countryService.deleteCountry(code);
        return ResponseEntity.noContent().build();
    }
}
