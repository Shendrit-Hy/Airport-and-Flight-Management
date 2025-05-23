package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.config.TenantContext;
import com.mbi_re.airport_management.model.CurrencyRate;
import com.mbi_re.airport_management.repository.CurrencyRateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CurrencyRateService {

    @Autowired
    private CurrencyRateRepository currencyRateRepository;

    /**
     * Retrieves all currency rates associated with the current tenant.
     * <p>
     * The result is cached with a key based on the tenant ID to improve performance.
     * The cache is skipped if the result is null or empty.
     *
     * @return a list of CurrencyRate objects for the current tenant
     */
    @Cacheable(value = "currencyRates", key = "'getAllRates_' + T(com.mbi_re.airport_management.config.TenantContext).getTenantId()", unless = "#result == null or #result.isEmpty()")
    public List<CurrencyRate> getAllRates() {
        String tenantId = TenantContext.getTenantId();
        return currencyRateRepository.findAllByTenantId(tenantId);
    }

    /**
     * Saves or updates a currency rate for the current tenant.
     * <p>
     * The tenant ID is automatically set on the CurrencyRate entity before saving.
     *
     * @param rate the CurrencyRate entity to save or update
     * @return the persisted CurrencyRate entity
     */
    public CurrencyRate saveRate(CurrencyRate rate) {
        rate.setTenantId(TenantContext.getTenantId());
        return currencyRateRepository.save(rate);
    }

    /**
     * Finds a currency rate by its currency code for the current tenant.
     *
     * @param code the currency code (e.g., "USD")
     * @return an Optional containing the CurrencyRate if found, or empty if not found
     */
    public Optional<CurrencyRate> findByCode(String code) {
        return currencyRateRepository.findByCodeAndTenantId(code, TenantContext.getTenantId());
    }

    /**
     * Deletes a currency rate by its currency code for the current tenant.
     *
     * @param code the currency code identifying the rate to delete
     */
    public void deleteByCode(String code) {
        currencyRateRepository.deleteByCodeAndTenantId(code, TenantContext.getTenantId());
    }
}
