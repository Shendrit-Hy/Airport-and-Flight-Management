package com.mbi_re.airport_management.repository;

import com.mbi_re.airport_management.model.Policy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * {@code PolicyRepository} ofron metoda për qasjen dhe menaxhimin e entiteteve {@link Policy},
 * me filtrime të përshtatura për çdo tenant në sistemin multi-tenant.
 */
@Repository
public interface PolicyRepository extends JpaRepository<Policy, Long> {

    /**
     * Gjen të gjitha politikat (policies) që i përkasin një tenant-i të caktuar.
     *
     * @param tenantId identifikuesi i tenant-it
     * @return listë me politika për tenant-in përkatës
     */
    List<Policy> findByTenantId(String tenantId);

    /**
     * Fshin një politikë specifike duke përdorur ID-në dhe tenant-id-in.
     *
     * @param id       ID-ja e politikës
     * @param tenantId identifikuesi i tenant-it
     */
    void deleteByIdAndTenantId(Long id, String tenantId);
}
