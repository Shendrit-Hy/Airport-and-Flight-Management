package com.mbi_re.airport_management.repository;

import com.mbi_re.airport_management.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for managing Payment entities.
 */
@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    /**
     * Retrieves all payments for a specific tenant.
     *
     * @param tenantId the tenant ID
     * @return list of payments associated with the tenant
     */
    List<Payment> findAllByTenantId(String tenantId);

    /**
     * Finds a payment by its reference and tenant ID.
     *
     * @param reference the unique payment reference
     * @param tenantId  the tenant ID
     * @return the payment matching the reference and tenant, or null if not found
     */
    Payment findByReferenceAndTenantId(String reference, String tenantId);
}
