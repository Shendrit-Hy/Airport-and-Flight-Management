package com.mbi_re.airport_management.repository;

import com.mbi_re.airport_management.model.Airport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * {@code AirportRepository} është një interface që ofron qasje në të dhënat e aeroportit,
 * duke mbështetur operacionet bazë dhe kërkesa të personalizuara për arkitekturën multi-tenant.
 */
public interface AirportRepository extends JpaRepository<Airport, Long> {

    /**
     * Gjen të gjitha aeroportet që i përkasin një tenant-i të caktuar.
     *
     * @param tenantId identifikuesi i tenant-it
     * @return një listë me aeroportet përkatëse
     */
    List<Airport> findByTenantId(String tenantId);

    /**
     * Gjen një aeroport bazuar në ID-në e tij dhe tenantId.
     *
     * @param id       ID-ja e aeroportit
     * @param tenantId identifikuesi i tenant-it
     * @return {@link Optional} që përmban aeroportin nëse ekziston
     */
    Optional<Airport> findByIdAndTenantId(Long id, String tenantId);

    /**
     * Fshin një aeroport bazuar në ID dhe tenantId.
     *
     * @param id       ID-ja e aeroportit për t'u fshirë
     * @param tenantId identifikuesi i tenant-it që ka të drejtë për këtë veprim
     */
    void deleteByIdAndTenantId(Long id, String tenantId);
}
