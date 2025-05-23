package com.mbi_re.airport_management.controller;

import com.mbi_re.airport_management.model.CurrencyRate;
import com.mbi_re.airport_management.service.CurrencyRateService;
import com.mbi_re.airport_management.utils.TenantUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing currency rates.
 * Supports public viewing and admin-level modifications, with tenant-aware access.
 */
@RestController
@RequestMapping("/currency-rates")
@Tag(name = "Currency Rate Management", description = "Manage currency rates per tenant with admin-level modifications")
@RequiredArgsConstructor
public class CurrencyRateController {

    @Autowired
    private CurrencyRateService currencyRateService;

    /**
     * Retrieves all currency rates for the current tenant.
     * <p>
     * If the request is unauthenticated, tenant ID is validated from the "X-Tenant-ID" header.
     * If authenticated, tenant is extracted from JWT and validated.
     *
     * @param request HTTP request containing headers, including Authorization and X-Tenant-ID
     * @return List of CurrencyRate objects for the tenant
     */
    @GetMapping
    @Operation(
            summary = "Get all currency rates",
            description = "Retrieve all currency rates for the current tenant. Tenant ID is validated either from the JWT or X-Tenant-ID header."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Currency rates retrieved successfully"),
            @ApiResponse(responseCode = "403", description = "Invalid or missing tenant ID")
    })
    public List<CurrencyRate> getAllRates(
            @Parameter(hidden = true) HttpServletRequest request) {

        String jwt = request.getHeader("Authorization");
        if (jwt == null || jwt.isBlank()) {
            String headerTenantId = request.getHeader("X-Tenant-ID");
            TenantUtil.validateTenant(headerTenantId);
        } else {
            TenantUtil.validateTenantFromContext();
        }
        return currencyRateService.getAllRates();
    }

    /**
     * Creates or updates a currency rate for the current tenant.
     * Access restricted to users with ADMIN role.
     *
     * @param rate    The CurrencyRate object to create or update
     * @param request HTTP request used for tenant validation context
     * @return The saved CurrencyRate object
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Create or update currency rate",
            description = "Create a new or update an existing currency rate. Admin-only endpoint."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Currency rate saved successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied or invalid tenant context")
    })
    public CurrencyRate addRate(
            @RequestBody
            @Parameter(description = "Currency rate to create or update", required = true)
            CurrencyRate rate,
            @Parameter(hidden = true) HttpServletRequest request) {

        TenantUtil.validateTenantFromContext();
        return currencyRateService.saveRate(rate);
    }

    /**
     * Updates an existing currency rate identified by its currency code.
     * Access restricted to users with ADMIN role.
     *
     * @param code    The currency code to update
     * @param rate    The updated currency rate data
     * @param request HTTP request used for tenant validation context
     * @return ResponseEntity containing the updated CurrencyRate
     */
    @PutMapping("/{code}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Update currency rate by code",
            description = "Update an existing currency rate by currency code. Admin-only endpoint."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Currency rate updated successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied or invalid tenant context"),
            @ApiResponse(responseCode = "404", description = "Currency rate not found")
    })
    public ResponseEntity<CurrencyRate> updateRate(
            @PathVariable
            @Parameter(description = "Currency code to update", required = true)
            String code,
            @RequestBody
            @Parameter(description = "Updated currency rate data", required = true)
            CurrencyRate rate,
            @Parameter(hidden = true) HttpServletRequest request) {

        TenantUtil.validateTenantFromContext();
        rate.setCode(code);
        CurrencyRate updated = currencyRateService.saveRate(rate);
        return ResponseEntity.ok(updated);
    }

    /**
     * Deletes a currency rate by its currency code for the current tenant.
     * Access restricted to users with ADMIN role.
     *
     * @param code    The currency code of the rate to delete
     * @param request HTTP request used for tenant validation context
     * @return ResponseEntity with HTTP status 204 No Content if successful
     */
    @DeleteMapping("/{code}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Delete currency rate by code",
            description = "Delete a currency rate by its code. Admin-only endpoint."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Currency rate deleted successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied or invalid tenant context"),
            @ApiResponse(responseCode = "404", description = "Currency rate not found")
    })
    public ResponseEntity<Void> deleteRate(
            @PathVariable
            @Parameter(description = "Currency code to delete", required = true)
            String code,
            @Parameter(hidden = true) HttpServletRequest request) {

        TenantUtil.validateTenantFromContext();
        currencyRateService.deleteByCode(code);
        return ResponseEntity.noContent().build();
    }
}
