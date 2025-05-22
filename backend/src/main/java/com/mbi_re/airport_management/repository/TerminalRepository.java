package com.mbi_re.airport_management.repository;

import com.mbi_re.airport_management.model.Terminal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for accessing terminal data.
 * Supports tenant-aware queries.
 */
public interface TerminalRepository extends JpaRepository<Terminal, Long> {

    /**
     * Finds all terminals associated with a given tenant.
     *
     * @param tenantId the ID of the tenant
     * @return list of terminals
     */
    List<Terminal> findByTenantId(String tenantId);

    /**
     * Finds a specific terminal by ID and tenant.
     *
     * @param id       the ID of the terminal
     * @param tenantId the tenant ID
     * @return optional terminal
     */
    Optional<Terminal> findByIdAndTenantId(Long id, String tenantId);
}
