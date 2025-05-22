package com.mbi_re.airport_management.repository;

import com.mbi_re.airport_management.model.Faq;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing FAQ entities with tenant-based filtering.
 */
@Repository
public interface FaqRepository extends JpaRepository<Faq, Long> {

    /**
     * Retrieves all FAQs associated with the given tenant ID.
     *
     * @param tenantId the tenant identifier
     * @return list of FAQs for the tenant
     */
    List<Faq> findAllByTenantId(String tenantId);

    /**
     * Finds a specific FAQ by ID and tenant ID.
     *
     * @param id the FAQ ID
     * @param tenantId the tenant identifier
     * @return optional containing the FAQ if found
     */
    Optional<Faq> findByIdAndTenantId(Long id, String tenantId);
}
