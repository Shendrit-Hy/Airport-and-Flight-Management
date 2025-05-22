package com.mbi_re.airport_management.repository;

import com.mbi_re.airport_management.model.Maintenance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository for managing {@link Maintenance} entities with tenant-based filtering.
 */
public interface MaintenanceRepository extends JpaRepository<Maintenance, Long> {

    /**
     * Finds maintenance records by airport ID and status for a specific tenant.
     *
     * @param airportID the airport code
     * @param status    the maintenance status
     * @param tenantId  the tenant identifier
     * @return list of matching maintenance records
     */
    List<Maintenance> findByAirportIDAndStatusAndTenantId(String airportID, String status, String tenantId);

    /**
     * Finds maintenance records by airport ID for a specific tenant.
     *
     * @param airportID the airport code
     * @param tenantId  the tenant identifier
     * @return list of maintenance records
     */
    List<Maintenance> findByAirportIDAndTenantId(String airportID, String tenantId);

    /**
     * Finds maintenance records by status for a specific tenant.
     *
     * @param status    the maintenance status
     * @param tenantId  the tenant identifier
     * @return list of matching records
     */
    List<Maintenance> findByStatusAndTenantId(String status, String tenantId);

    /**
     * Finds all maintenance records for a specific tenant.
     *
     * @param tenantId the tenant identifier
     * @return list of maintenance records
     */
    List<Maintenance> findAllByTenantId(String tenantId);

    /**
     * Finds a maintenance record by ID and tenant ID.
     *
     * @param id       the record ID
     * @param tenantId the tenant identifier
     * @return the maintenance record or null
     */
    Maintenance findByIdAndTenantId(Long id, String tenantId);
}
