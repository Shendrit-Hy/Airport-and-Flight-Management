package com.mbi_re.airport_management.repository;

import com.mbi_re.airport_management.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findAllByTenantId(String tenantId);
    Payment findByReferenceAndTenantId(String reference, String tenantId);

}
