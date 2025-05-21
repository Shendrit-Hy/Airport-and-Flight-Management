package com.mbi_re.airport_management.repository;

import com.mbi_re.airport_management.model.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing Staff entities.
 */
@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {

    /**
     * Finds a staff member by ID and tenant ID.
     *
     * @param id the staff ID
     * @param tenantId the tenant ID
     * @return an Optional containing the matching Staff if found
     */
    Optional<Staff> findByIdAndTenantId(Long id, String tenantId);

    /**
     * Retrieves all staff members for a given tenant.
     *
     * @param tenantId the tenant ID
     * @return a list of matching Staff entities
     */
    List<Staff> findByTenantId(String tenantId);
}