package com.mbi_re.airport_management.repository;

import com.mbi_re.airport_management.model.ParkingPrice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for managing ParkingPrice entities with tenant-based filtering.
 */
public interface ParkingPriceRepository extends JpaRepository<ParkingPrice, Long> {

    /**
     * Retrieves all parking prices associated with a specific tenant.
     *
     * @param tenantId the tenant identifier
     * @return list of ParkingPrice entities
     */
    List<ParkingPrice> findAllByTenantId(String tenantId);
}
