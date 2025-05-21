package com.mbi_re.airport_management.repository;

import com.mbi_re.airport_management.model.Terminal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TerminalRepository extends JpaRepository<Terminal, Long> {

    // Find all terminals for a given tenant
    List<Terminal> findByTenantId(String tenantId);

    // Find one terminal by ID and tenant
    Optional<Terminal> findByIdAndTenantId(Long id, String tenantId);
}
