package com.mbi_re.airport_management.repository;

import com.mbi_re.airport_management.model.Gate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GateRepository extends JpaRepository<Gate, Long> {

    /**
     * Retrieves all gates for the specified tenant.
     *
     * @param tenantId the tenant identifier
     * @return a list of gates belonging to the given tenant
     */
    List<Gate> findByTenantId(String tenantId);

    /**
     * Retrieves a gate by its ID and tenant.
     *
     * @param id the gate ID
     * @param tenantId the tenant identifier
     * @return an Optional containing the gate if found, or empty if not
     */
    Optional<Gate> findByIdAndTenantId(Long id, String tenantId);
}
