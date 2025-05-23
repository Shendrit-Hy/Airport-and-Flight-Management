package com.mbi_re.airport_management.repository;

import com.mbi_re.airport_management.model.ParkingPrice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * {@code ParkingPriceRepository} ofron operacione për qasjen dhe menaxhimin e entiteteve {@link ParkingPrice}
 * në një ambient multi-tenant, duke përdorur filtrimin sipas {@code tenantId}.
 */
public interface ParkingPriceRepository extends JpaRepository<ParkingPrice, Long> {

    /**
     * Kthen të gjitha çmimet e parkingut që i përkasin një tenant-i të caktuar.
     *
     * @param tenantId identifikuesi i tenant-it
     * @return listë me entitete {@link ParkingPrice}
     */
    List<ParkingPrice> findAllByTenantId(String tenantId);
}
