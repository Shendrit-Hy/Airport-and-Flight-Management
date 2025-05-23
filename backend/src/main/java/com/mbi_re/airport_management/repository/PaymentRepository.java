package com.mbi_re.airport_management.repository;

import com.mbi_re.airport_management.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * {@code PaymentRepository} ofron funksionalitete për qasjen dhe menaxhimin e entiteteve {@link Payment},
 * duke siguruar izolim të të dhënave për secilin tenant në një arkitekturë multi-tenant.
 */
@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    /**
     * Kthen të gjitha pagesat që i përkasin një tenant-i specifik.
     *
     * @param tenantId identifikuesi i tenant-it
     * @return listë me pagesa për tenant-in përkatës
     */
    List<Payment> findAllByTenantId(String tenantId);

    /**
     * Gjen një pagesë bazuar në referencën unike dhe tenant-in përkatës.
     *
     * @param reference referenca unike e pagesës (p.sh. kodi i transaksionit ose rezervimit)
     * @param tenantId  identifikuesi i tenant-it
     * @return objekti {@link Payment} nëse ekziston, përndryshe {@code null}
     */
    Payment findByReferenceAndTenantId(String reference, String tenantId);
}
