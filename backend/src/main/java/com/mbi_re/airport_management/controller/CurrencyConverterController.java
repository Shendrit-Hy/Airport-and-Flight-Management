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
 * REST controller for currency conversion and rate management.
 */
@RestController
@RequestMapping("/convert")
@Tag(name = "Currency Conversion", description = "Endpoints for converting currencies and managing currency rates.")
@RequiredArgsConstructor
public class CurrencyConverterController {

    @Autowired
    private CurrencyConverterService converterService;

    /**
     * Converts an amount from one currency to another using tenant-provided rates.
     * Publicly accessible by sending the tenant in the X-Tenant-ID header.
     */
    @GetMapping
    @Operation(summary = "Convert currency",
            description = "Convert an amount from one currency to another. Public endpoint, requires tenant in header.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Conversion successful"),
            @ApiResponse(responseCode = "403", description = "Invalid or missing tenant ID")
    })
    @Cacheable(value = "conversionResults", key = "#tenantId + ':' + #from + ':' + #to + ':' + #value")
    public ResponseEntity<Map<String, Object>> convertCurrency(
            @RequestHeader("X-Tenant-ID")
            @Parameter(description = "Tenant ID from header") String tenantId,
            @RequestParam @Parameter(description = "Source currency code") String from,
            @RequestParam @Parameter(description = "Target currency code") String to,
            @RequestParam @Parameter(description = "Amount to convert") double value) {

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
     * Accessible only to users with ADMIN role.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/rate")
    @Operation(summary = "Add currency rate", description = "Add a new currency rate. Admin-only.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Rate added successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    public CurrencyRate addRate(
            @RequestBody @Parameter(description = "Currency rate to add") CurrencyRate rate) {
        TenantUtil.validateTenantFromContext();
        return converterService.addRate(rate);
    }

    /**
     * Updates a currency rate identified by code.
     * Accessible only to users with ADMIN role.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/rate/{code}")
    @Operation(summary = "Update currency rate", description = "Update a currency rate. Admin-only.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Rate updated successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    public CurrencyRate updateRate(
            @PathVariable @Parameter(description = "Currency code to update") String code,
            @RequestBody @Parameter(description = "Updated rate") CurrencyRate rate) {
        TenantUtil.validateTenantFromContext();
        return converterService.updateRate(code, rate);
    }

    /**
     * Deletes a currency rate by code.
     * Accessible only to users with ADMIN role.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/rate/{code}")
    @Operation(summary = "Delete currency rate", description = "Delete a currency rate by code. Admin-only.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Rate deleted successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    public ResponseEntity<Void> deleteRate(
            @PathVariable @Parameter(description = "Currency code to delete") String code) {
        TenantUtil.validateTenantFromContext();
        converterService.deleteRate(code);
        return ResponseEntity.noContent().build();
    }

    /**
     * Lists all currency rates for the current tenant.
     * Publicly accessible using the tenant header.
     */
    @GetMapping("/rate")
    @Operation(summary = "List currency rates", description = "Get all currency rates for the specified tenant. Public.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Rates retrieved successfully"),
            @ApiResponse(responseCode = "403", description = "Invalid tenant")
    })
    @Cacheable(value = "currencyRates", key = "#tenantId")
    public List<CurrencyRate> listRates(
            @RequestHeader("X-Tenant-ID")
            @Parameter(description = "Tenant ID from header") String tenantId) {
        TenantUtil.validateTenant(tenantId);
        return converterService.listRates(tenantId);
    }
}
