package com.mbi_re.airport_management.repository;

import com.mbi_re.airport_management.model.CurrencyRate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * {@code CurrencyRateRepository} ofron funksionalitete për menaxhimin e kurseve valutore
 * në një mjedis multi-tenant, duke mundësuar izolimin e të dhënave për çdo tenant.
 */
public interface CurrencyRateRepository extends JpaRepository<CurrencyRate, String> {

    /**
     * Gjen të gjitha kurset valutore për një tenant të caktuar.
     *
     * @param tenantId identifikuesi i tenant-it
     * @return një listë me kurset valutore
     */
    List<CurrencyRate> findAllByTenantId(String tenantId);

    /**
     * Gjen një kurs valutor sipas kodit të valutës dhe tenant-it përkatës.
     *
     * @param code     kodi i valutës (p.sh., "EUR", "USD")
     * @param tenantId identifikuesi i tenant-it
     * @return {@link Optional} me kursin nëse ekziston
     */
    Optional<CurrencyRate> findByCodeAndTenantId(String code, String tenantId);

    /**
     * Fshin një kurs valutor bazuar në kodin e valutës dhe tenant-id-in.
     *
     * @param code     kodi i valutës
     * @param tenantId identifikuesi i tenant-it
     */
    void deleteByCodeAndTenantId(String code, String tenantId);
}
