package com.mbi_re.airport_management.repository;

import com.mbi_re.airport_management.model.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for accessing passenger data with tenant isolation.
 */
@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Long> {

    /**
     * Finds all passengers belonging to the given tenant.
     *
     * @param tenantId the tenant identifier
     * @return list of passengers for the tenant
     */
    List<Passenger> findAllByTenantId(String tenantId);

    /**
     * Finds a passenger by ID and tenant ID.
     *
     * @param id       the passenger ID
     * @param tenantId the tenant identifier
     * @return optional passenger for the given ID and tenant
     */
    Optional<Passenger> findByIdAndTenantId(Long id, String tenantId);

    /**
     * Deletes a passenger by ID and tenant ID.
     *
     * @param id       the passenger ID
     * @param tenantId the tenant identifier
     */
    void deleteByIdAndTenantId(Long id, String tenantId);
}
