package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.config.TenantContext;
import com.mbi_re.airport_management.model.CurrencyRate;
import com.mbi_re.airport_management.repository.CurrencyRateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CurrencyRateService {

    private final CurrencyRateRepository currencyRateRepository;

    /**
     * Retrieves all currency rates for the current tenant.
     * Results are cached unless the result is null or empty.
     *
     * @return list of currency rates
     */
    @Cacheable(value = "currencyRates", key = "'getAllRates_' + T(com.mbi_re.airport_management.config.TenantContext).getTenantId()", unless = "#result == null or #result.isEmpty()")
    public List<CurrencyRate> getAllRates() {
        String tenantId = TenantContext.getTenantId();
        return currencyRateRepository.findAllByTenantId(tenantId);
    }

    /**
     * Saves or updates a currency rate for the current tenant.
     *
     * @param rate the currency rate to save
     * @return the saved currency rate
     */
    public CurrencyRate saveRate(CurrencyRate rate) {
        rate.setTenantId(TenantContext.getTenantId());
        return currencyRateRepository.save(rate);
    }

    /**
     * Finds a currency rate by code for the current tenant.
     *
     * @param code the currency code
     * @return an optional containing the found currency rate
     */
    public Optional<CurrencyRate> findByCode(String code) {
        return currencyRateRepository.findByCodeAndTenantId(code, TenantContext.getTenantId());
    }

    /**
     * Deletes a currency rate by code for the current tenant.
     *
     * @param code the currency code
     */
    public void deleteByCode(String code) {
        currencyRateRepository.deleteByCodeAndTenantId(code, TenantContext.getTenantId());
    }
}
