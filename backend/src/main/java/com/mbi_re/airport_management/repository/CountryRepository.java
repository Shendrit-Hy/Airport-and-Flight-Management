package com.mbi_re.airport_management.repository;

import com.mbi_re.airport_management.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * {@code CountryRepository} ofron operacione për menaxhimin e entiteteve {@link Country},
 * duke përfshirë mbështetje për izolimin e të dhënave për secilin tenant.
 */
public interface CountryRepository extends JpaRepository<Country, Long> {

    /**
     * Kontrollon nëse ekziston një vend me emrin e dhënë për një tenant specifik.
     *
     * @param name     emri i vendit
     * @param tenantId identifikuesi i tenant-it
     * @return {@code true} nëse ekziston, përndryshe {@code false}
     */
    boolean existsByNameAndTenantId(String name, String tenantId);

    /**
     * Gjen një vend sipas emrit dhe tenantId-it.
     *
     * @param name     emri i vendit
     * @param tenantId identifikuesi i tenant-it
     * @return {@link Optional} me vendin nëse ekziston
     */
    Optional<Country> findByNameAndTenantId(String name, String tenantId);

    /**
     * Kthen të gjitha vendet për një tenant të caktuar.
     *
     * @param tenantId identifikuesi i tenant-it
     * @return listë me vendet përkatëse
     */
    List<Country> findAllByTenantId(String tenantId);

    /**
     * Fshin një vend bazuar në kodin e tij dhe tenantId.
     *
     * @param code     kodi i vendit (p.sh., "AL", "DE", "US")
     * @param tenantId identifikuesi i tenant-it
     */
    void deleteByCodeAndTenantId(String code, String tenantId);

    /**
     * Fshin një vend bazuar në ID-në dhe tenantId-in përkatës.
     *
     * @param id       ID-ja e vendit
     * @param tenantId identifikuesi i tenant-it
     */
    void deleteByIdAndTenantId(Long id, String tenantId);
}
