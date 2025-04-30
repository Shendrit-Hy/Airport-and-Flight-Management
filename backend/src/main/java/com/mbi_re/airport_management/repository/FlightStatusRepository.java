package com.mbi_re.airport_management.repository;

import com.mbi_re.airport_management.model.FlightStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FlightStatusRepository extends JpaRepository<FlightStatus, Long> {
    Optional<FlightStatus> findByIdAndTenantId(Long id, String tenantId);
    List<FlightStatus> findByStatusAndTenantId(String status, String tenantId);
}
