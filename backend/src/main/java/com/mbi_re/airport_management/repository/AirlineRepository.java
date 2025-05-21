package com.mbi_re.airport_management.repository;

import com.mbi_re.airport_management.model.Airline;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AirlineRepository extends JpaRepository<Airline, Long> {

    /**
     * Checks if an airline exists by name for a specific tenant.
     *
     * @param name     the airline name
     * @param tenantId the tenant identifier
     * @return true if the airline exists, false otherwise
     */
    boolean existsByNameAndTenantId(String name, String tenantId);

    /**
     * Retrieves all airlines for the specified tenant.
     *
     * @param tenantId the tenant identifier
     * @return list of airlines belonging to the tenant
     */
    List<Airline> findByTenantId(String tenantId);
}
