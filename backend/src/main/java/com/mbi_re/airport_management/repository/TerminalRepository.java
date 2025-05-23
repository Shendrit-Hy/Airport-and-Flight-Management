package com.mbi_re.airport_management.repository;

import com.mbi_re.airport_management.model.Terminal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * {@code TerminalRepository} ofron operacione për qasje dhe menaxhim të të dhënave për {@link Terminal},
 * duke mbështetur filtrimin sipas {@code tenantId} për sisteme me arkitekturë multi-tenant.
 */
public interface TerminalRepository extends JpaRepository<Terminal, Long> {

    /**
     * Gjen të gjitha terminalet që i përkasin një tenant-i të caktuar.
     *
     * @param tenantId identifikuesi i tenant-it
     * @return listë me entitete {@link Terminal}
     */
    List<Terminal> findByTenantId(String tenantId);

    /**
     * Gjen një terminal specifik sipas ID-së dhe tenant-it.
     *
     * @param id       ID-ja e terminalit
     * @param tenantId identifikuesi i tenant-it
     * @return {@link Optional} që përmban terminalin nëse gjendet
     */
    Optional<Terminal> findByIdAndTenantId(Long id, String tenantId);
}
