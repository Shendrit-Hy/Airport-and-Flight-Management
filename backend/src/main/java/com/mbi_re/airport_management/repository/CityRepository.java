package com.mbi_re.airport_management.repository;

import com.mbi_re.airport_management.model.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * {@code CityRepository} ofron funksionalitete për menaxhimin e entiteteve {@link City},
 * duke filtruar të dhënat sipas tenant-it për mbështetje në arkitekturë multi-tenant.
 */
public interface CityRepository extends JpaRepository<City, Long> {

    /**
     * Gjen të gjitha qytetet që i përkasin një tenant-i të caktuar.
     *
     * @param tenantId identifikuesi i tenant-it
     * @return një listë me qytete
     */
    List<City> findAllByTenantId(String tenantId);

    /**
     * Gjen qytetet për një vend specifik të filtruar sipas tenant-it.
     *
     * @param countryId ID-ja e vendit
     * @param tenantId identifikuesi i tenant-it
     * @return një listë me qytete që i përkasin vendit dhe tenant-it të dhënë
     */
    List<City> findByCountryIdAndTenantId(Long countryId, String tenantId);

    /**
     * Fshin një qytet bazuar në ID-në dhe tenant-in përkatës.
     *
     * @param id ID-ja e qytetit për t'u fshirë
     * @param tenantId identifikuesi i tenant-it
     */
    void deleteByIdAndTenantId(Long id, String tenantId);
}
