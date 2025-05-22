package com.mbi_re.airport_management.repository;

import com.mbi_re.airport_management.model.Support;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for managing Support entity persistence.
 */
@Repository
@Tag(name = "Support Repository", description = "Handles database operations for support tickets.")
public interface SupportRepository extends JpaRepository<Support, Long> {

    /**
     * Retrieves all support tickets for a given tenant.
     *
     * @param tenantId the tenant ID
     * @return list of support tickets
     */
    List<Support> findByTenantId(String tenantId);

    /**
     * Finds a support ticket by ID and tenant ID.
     *
     * @param supportId the support ID
     * @param tenantId  the tenant ID
     * @return optional support ticket
     */
    Optional<Support> findByIdAndTenantId(Long supportId, String tenantId);
}
