package com.mbi_re.airport_management.repository;

import com.mbi_re.airport_management.model.Gate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GateRepository extends JpaRepository<Gate, Long> {
    List<Gate> findByTenantId(String tenantId);
}
