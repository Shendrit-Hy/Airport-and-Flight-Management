package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.config.TenantContext;
import com.mbi_re.airport_management.model.CurrencyRate;
import com.mbi_re.airport_management.repository.CurrencyRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for converting currency and managing currency rates
 * in a multi-tenant environment.
 */
@Service
public class CurrencyConverterService {

    @Autowired
    private CurrencyRateRepository currencyRateRepository;

    /**
     * Converts an amount from one currency to another using tenant-specific exchange rates.
     * The conversion is cached per unique combination of source, target currency,
     * amount, and tenant ID to improve performance.
     *
     * @param from  the source currency code (e.g., "EUR")
     * @param to    the target currency code (e.g., "USD")
     * @param value the amount to convert
     * @return the converted amount in the target currency
     * @throws RuntimeException if either the source or target currency is not found for the tenant
     */
    @Cacheable(
            value = "currencyConversion",
            key = "#from + '_' + #to + '_' + #value + '_' + T(com.mbi_re.airport_management.config.TenantContext).getTenantId()",
            unless = "#result == null"
    )
    public double convert(String from, String to, double value) {
        String tenantId = TenantContext.getTenantId();

        CurrencyRate fromRate = currencyRateRepository.findByCodeAndTenantId(from.toUpperCase(), tenantId)
                .orElseThrow(() -> new RuntimeException("Currency not found: " + from));
        CurrencyRate toRate = currencyRateRepository.findByCodeAndTenantId(to.toUpperCase(), tenantId)
                .orElseThrow(() -> new RuntimeException("Currency not found: " + to));

        double usdValue = value / fromRate.getRateToUSD();
        return usdValue * toRate.getRateToUSD();
    }

    /**
     * Adds a new currency rate for the current tenant.
     * Evicts all related caches to ensure cache consistency.
     *
     * @param rate the CurrencyRate entity to add
     * @return the saved CurrencyRate entity
     */
    @CacheEvict(value = {"currencyRates", "currencyConversion"}, allEntries = true)
    public CurrencyRate addRate(CurrencyRate rate) {
        String tenantId = TenantContext.getTenantId();
        rate.setTenantId(tenantId);
        return currencyRateRepository.save(rate);
    }

    /**
     * Updates an existing currency rate for the current tenant.
     * Evicts all related caches to ensure cache consistency.
     *
     * @param code the currency code identifying the rate to update
     * @param rate the CurrencyRate entity containing updated information
     * @return the updated CurrencyRate entity
     */
    @CacheEvict(value = {"currencyRates", "currencyConversion"}, allEntries = true)
    public CurrencyRate updateRate(String code, CurrencyRate rate) {
        String tenantId = TenantContext.getTenantId();
        rate.setCode(code);
        rate.setTenantId(tenantId);
        return currencyRateRepository.save(rate);
    }

    /**
     * Deletes a currency rate by its currency code for the current tenant.
     * Evicts all related caches to maintain cache correctness.
     *
     * @param code the currency code of the rate to delete
     */
    @CacheEvict(value = {"currencyRates", "currencyConversion"}, allEntries = true)
    public void deleteRate(String code) {
        currencyRateRepository.deleteById(code);
    }

    /**
     * Retrieves all currency rates for the specified tenant.
     * The result is cached by tenant ID to improve retrieval speed.
     *
     * @param tenantId the tenant identifier
     * @return list of CurrencyRate entities for the tenant
     */
    @Cacheable(value = "currencyRates", key = "#tenantId", unless = "#result == null or #result.isEmpty()")
    public List<CurrencyRate> listRates(String tenantId) {
        return currencyRateRepository.findAllByTenantId(tenantId);
    }
}
