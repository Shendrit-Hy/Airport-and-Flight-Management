package com.mbi_re.airport_management.controller;

import com.mbi_re.airport_management.config.TenantContext;
import com.mbi_re.airport_management.model.CurrencyRate;
import com.mbi_re.airport_management.service.CurrencyConverterService;
import com.mbi_re.airport_management.utils.TenantUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * REST controller for currency conversion and currency rate management.
 * Provides endpoints to convert currencies and manage currency rates per tenant.
 */
@RestController
@RequestMapping("/convert")
@Tag(name = "Currency Conversion", description = "Endpoints for converting currencies and managing currency rates.")
@RequiredArgsConstructor
public class CurrencyConverterController {

    @Autowired
    private CurrencyConverterService converterService;

    /**
     * Converts a specified amount from one currency to another for the given tenant.
     * This is a public endpoint requiring tenant identification through the "X-Tenant-ID" header.
     *
     * @param tenantId The tenant ID provided in the request header.
     * @param from     The source currency code (e.g., "USD").
     * @param to       The target currency code (e.g., "EUR").
     * @param value    The amount to be converted.
     * @return ResponseEntity containing a map with conversion details including original and converted amounts.
     */
    @GetMapping
    @Operation(
            summary = "Convert currency",
            description = "Convert an amount from one currency to another. Public endpoint, requires tenant ID in header."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Conversion successful"),
            @ApiResponse(responseCode = "403", description = "Invalid or missing tenant ID")
    })
    @Cacheable(value = "conversionResults", key = "#tenantId + ':' + #from + ':' + #to + ':' + #value")
    public ResponseEntity<Map<String, Object>> convertCurrency(
            @RequestHeader("X-Tenant-ID")
            @Parameter(description = "Tenant ID from header", required = true)
            String tenantId,
            @RequestParam
            @Parameter(description = "Source currency code", required = true)
            String from,
            @RequestParam
            @Parameter(description = "Target currency code", required = true)
            String to,
            @RequestParam
            @Parameter(description = "Amount to convert", required = true)
            double value) {

        TenantUtil.validateTenant(tenantId);
        double result = converterService.convert(from, to, value);

        Map<String, Object> response = new HashMap<>();
        response.put("from", from);
        response.put("to", to);
        response.put("originalAmount", value);
        response.put("convertedAmount", result);

        return ResponseEntity.ok(response);
    }

    /**
     * Adds a new currency rate for the current tenant.
     * Access restricted to users with ADMIN role.
     *
     * @param rate CurrencyRate object containing currency code and rate details.
     * @return The created CurrencyRate object.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/rate")
    @Operation(
            summary = "Add currency rate",
            description = "Add a new currency rate. Admin-only endpoint."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Rate added successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    public CurrencyRate addRate(
            @RequestBody
            @Parameter(description = "Currency rate to add", required = true)
            CurrencyRate rate) {

        TenantUtil.validateTenantFromContext();
        return converterService.addRate(rate);
    }

    /**
     * Updates an existing currency rate identified by its currency code.
     * Access restricted to users with ADMIN role.
     *
     * @param code The currency code to update (e.g., "USD").
     * @param rate CurrencyRate object containing updated rate information.
     * @return The updated CurrencyRate object.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/rate/{code}")
    @Operation(
            summary = "Update currency rate",
            description = "Update a currency rate by its code. Admin-only endpoint."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Rate updated successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    public CurrencyRate updateRate(
            @PathVariable
            @Parameter(description = "Currency code to update", required = true)
            String code,
            @RequestBody
            @Parameter(description = "Updated currency rate details", required = true)
            CurrencyRate rate) {

        TenantUtil.validateTenantFromContext();
        return converterService.updateRate(code, rate);
    }

    /**
     * Deletes a currency rate by its currency code.
     * Access restricted to users with ADMIN role.
     *
     * @param code The currency code to delete (e.g., "USD").
     * @return ResponseEntity with HTTP status 204 No Content if deletion is successful.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/rate/{code}")
    @Operation(
            summary = "Delete currency rate",
            description = "Delete a currency rate by code. Admin-only endpoint."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Rate deleted successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    public ResponseEntity<Void> deleteRate(
            @PathVariable
            @Parameter(description = "Currency code to delete", required = true)
            String code) {

        TenantUtil.validateTenantFromContext();
        converterService.deleteRate(code);
        return ResponseEntity.noContent().build();
    }

    /**
     * Lists all currency rates for the specified tenant.
     * Public endpoint requiring tenant identification via the "X-Tenant-ID" header.
     *
     * @param tenantId The tenant ID provided in the request header.
     * @return List of CurrencyRate objects for the tenant.
     */
    @GetMapping("/rate")
    @Operation(
            summary = "List currency rates",
            description = "Retrieve all currency rates for the specified tenant. Public endpoint."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Rates retrieved successfully"),
            @ApiResponse(responseCode = "403", description = "Invalid tenant ID")
    })
    @Cacheable(value = "currencyRates", key = "#tenantId")
    public List<CurrencyRate> listRates(
            @RequestHeader("X-Tenant-ID")
            @Parameter(description = "Tenant ID from header", required = true)
            String tenantId) {

        TenantUtil.validateTenant(tenantId);
        return converterService.listRates(tenantId);
    }
}
