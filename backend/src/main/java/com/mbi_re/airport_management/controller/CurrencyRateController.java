package com.mbi_re.airport_management.controller;

import com.mbi_re.airport_management.config.TenantContext;
import com.mbi_re.airport_management.model.CurrencyRate;
import com.mbi_re.airport_management.service.CurrencyRateService;
import com.mbi_re.airport_management.utils.TenantUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class CurrencyRateController {

    private final CurrencyRateService currencyRateService;

    /**
     * Retrieves all currency rates for the current tenant.
     * <p>
     * - If the request is unauthenticated, tenant ID is validated from the X-Tenant-ID header.<br>
     * - If authenticated, tenant is extracted from JWT and validated.
     *
     * @param request HTTP request containing headers
     * @return list of currency rates
     */
    @GetMapping
    public List<CurrencyRate> getAllRates(HttpServletRequest request) {
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
     * Only accessible by ADMIN users.
     *
     * @param rate    the currency rate to create or update
     * @param request HTTP request for header validation
     * @return the saved currency rate
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public CurrencyRate addRate(@RequestBody CurrencyRate rate, HttpServletRequest request) {
        TenantUtil.validateTenantFromContext();
        return currencyRateService.saveRate(rate);
    }

    /**
     * Updates an existing currency rate by currency code.
     * Only accessible by ADMIN users.
     *
     * @param code    the currency code to update
     * @param rate    the updated currency rate data
     * @param request HTTP request for header validation
     * @return the updated currency rate
     */
    @PutMapping("/{code}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CurrencyRate> updateRate(@PathVariable String code,
                                                   @RequestBody CurrencyRate rate,
                                                   HttpServletRequest request) {
        TenantUtil.validateTenantFromContext();
        rate.setCode(code);
        CurrencyRate updated = currencyRateService.saveRate(rate);
        return ResponseEntity.ok(updated);
    }

    /**
     * Deletes a currency rate by code for the current tenant.
     * Only accessible by ADMIN users.
     *
     * @param code    the currency code to delete
     * @param request HTTP request for header validation
     * @return 204 No Content response if successful
     */
    @DeleteMapping("/{code}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteRate(@PathVariable String code, HttpServletRequest request) {
        TenantUtil.validateTenantFromContext();
        currencyRateService.deleteByCode(code);
        return ResponseEntity.noContent().build();
    }
}
