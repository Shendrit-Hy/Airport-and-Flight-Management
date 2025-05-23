package com.mbi_re.airport_management.repository;

import com.mbi_re.airport_management.model.Support;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * {@code SupportRepository} ofron operacione për menaxhimin e kërkesave për mbështetje teknike ({@link Support}),
 * duke mbështetur izolimin e të dhënave sipas {@code tenantId}.
 */
@Repository
@Tag(name = "Support Repository", description = "Handles database operations for support tickets.")
public interface SupportRepository extends JpaRepository<Support, Long> {

    /**
     * Kthen të gjitha kërkesat për mbështetje që i përkasin një tenant-i të caktuar.
     *
     * @param tenantId identifikuesi i tenant-it
     * @return listë me kërkesa për mbështetje
     */
    List<Support> findByTenantId(String tenantId);

    /**
     * Gjen një kërkesë specifike për mbështetje bazuar në ID dhe tenant.
     *
     * @param supportId ID-ja e kërkesës
     * @param tenantId  identifikuesi i tenant-it
     * @return {@link Optional} që përmban kërkesën nëse ekziston
     */
    Optional<Support> findByIdAndTenantId(Long supportId, String tenantId);
}
