package com.mbi_re.airport_management.repository;

import com.mbi_re.airport_management.model.CurrencyRate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CurrencyRateRepository extends JpaRepository<CurrencyRate, String> {

    /**
     * Finds all currency rates for the specified tenant.
     *
     * @param tenantId the tenant identifier
     * @return a list of currency rates
     */
    List<CurrencyRate> findAllByTenantId(String tenantId);

    /**
     * Finds a specific currency rate by code and tenant ID.
     *
     * @param code     the currency code
     * @param tenantId the tenant identifier
     * @return the currency rate, if found
     */
    Optional<CurrencyRate> findByCodeAndTenantId(String code, String tenantId);

    /**
     * Deletes a currency rate by code and tenant ID.
     *
     * @param code     the currency code
     * @param tenantId the tenant identifier
     */
    void deleteByCodeAndTenantId(String code, String tenantId);
}
