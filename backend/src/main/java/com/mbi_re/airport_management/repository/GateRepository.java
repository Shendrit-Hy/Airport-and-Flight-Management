package com.mbi_re.airport_management.repository;

import com.mbi_re.airport_management.model.Gate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * {@code GateRepository} ofron funksionalitete për qasjen dhe menaxhimin e entiteteve {@link Gate},
 * me filtrime të personalizuara bazuar në tenant për mbështetje të plotë multi-tenant.
 */
public interface GateRepository extends JpaRepository<Gate, Long> {

    /**
     * Gjen të gjitha portat (gates) që i përkasin një tenant-i specifik.
     *
     * @param tenantId identifikuesi i tenant-it
     * @return një listë me portat përkatëse
     */
    List<Gate> findByTenantId(String tenantId);

    /**
     * Gjen një portë sipas ID-së dhe tenant-it përkatës.
     *
     * @param id       ID-ja e portës
     * @param tenantId identifikuesi i tenant-it
     * @return {@link Optional} me portën nëse ekziston
     */
    Optional<Gate> findByIdAndTenantId(Long id, String tenantId);
}
