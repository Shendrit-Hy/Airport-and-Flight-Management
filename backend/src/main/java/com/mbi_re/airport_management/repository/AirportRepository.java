package com.mbi_re.airport_management.repository;

import com.mbi_re.airport_management.model.Airport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for accessing airport data per tenant.
 */
public interface AirportRepository extends JpaRepository<Airport, Long> {

    /**
     * Retrieves all airports for a specific tenant.
     *
     * @param tenantId the tenant identifier
     * @return list of airports for the tenant
     */
    List<Airport> findByTenantId(String tenantId);

    /**
     * Finds an airport by ID and tenant ID.
     *
     * @param id       the airport ID
     * @param tenantId the tenant identifier
     * @return optional airport
     */
    Optional<Airport> findByIdAndTenantId(Long id, String tenantId);

    /**
     * Deletes an airport by ID and tenant ID.
     *
     * @param id       the airport ID
     * @param tenantId the tenant identifier
     */
    void deleteByIdAndTenantId(Long id, String tenantId);
}
