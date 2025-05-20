package com.mbi_re.airport_management.repository;

import com.mbi_re.airport_management.model.Gate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GateRepository extends JpaRepository<Gate, Long> {

    // Find all gates for a given tenant
    List<Gate> findByTenantId(String tenantId);

    // Find one gate by ID and tenant
    Optional<Gate> findByIdAndTenantId(Long id, String tenantId);
}
