package com.mbi_re.airport_management.controller;

import com.mbi_re.airport_management.dto.CurrencyDTO;
import com.mbi_re.airport_management.service.CurrencyService;
import com.mbi_re.airport_management.utils.TenantUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing supported currencies per tenant.
 */
@RestController
@RequestMapping("/api/currencies")
@Tag(name = "Currency Management", description = "Manage supported currencies")
public class CurrencyController {

    private final CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    /**
     * Retrieves all supported currencies for a given tenant.
     * This is a public endpoint requiring tenant identification via the X-Tenant-ID header.
     *
     * @param tenantId Tenant ID from request header to identify tenant context
     * @return ResponseEntity containing the list of CurrencyDTOs
     */
    @Operation(
            summary = "Get all currencies",
            description = "Public endpoint to list all supported currencies for a tenant."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Currencies retrieved successfully"),
            @ApiResponse(responseCode = "403", description = "Invalid or missing tenant ID")
    })
    @GetMapping
    public ResponseEntity<List<CurrencyDTO>> getCurrencies(
            @RequestHeader("X-Tenant-ID")
            @Parameter(description = "Tenant ID from header", required = true)
            String tenantId) {

        TenantUtil.validateTenant(tenantId);
        return ResponseEntity.ok(currencyService.getAllCurrencies());
    }

    /**
     * Adds a new currency for the current tenant.
     * Access restricted to users with ADMIN role.
     *
     * @param currencyDTO Currency data to be added
     * @return ResponseEntity containing the added CurrencyDTO
     */
    @Operation(
            summary = "Add new currency",
            description = "Admin-only endpoint to add a new currency."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Currency added successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied or missing tenant context")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<CurrencyDTO> addCurrency(
            @RequestBody
            @Parameter(description = "Currency to add", required = true)
            CurrencyDTO currencyDTO) {

        TenantUtil.validateTenantFromContext();
        return ResponseEntity.ok(currencyService.addCurrency(currencyDTO));
    }

    /**
     * Deletes a currency by its code for the current tenant.
     * Access restricted to users with ADMIN role.
     *
     * @param code The currency code to delete (e.g., "USD", "EUR")
     * @return ResponseEntity with HTTP status 204 No Content if deletion is successful
     */
    @Operation(
            summary = "Delete a currency",
            description = "Admin-only endpoint to delete a currency by code."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Currency deleted successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied or missing tenant context")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{code}")
    public ResponseEntity<Void> deleteCurrency(
            @PathVariable
            @Parameter(description = "Currency code to delete", required = true)
            String code) {

        TenantUtil.validateTenantFromContext();
        currencyService.deleteCurrency(code);
        return ResponseEntity.noContent().build();
    }
}
