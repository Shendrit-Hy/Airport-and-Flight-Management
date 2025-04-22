package com.mbi_re.airport_management.repository;

import com.mbi_re.airport_management.model.Airport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AirportRepository extends JpaRepository<Airport, Long> {
    Optional<Airport> findByIdAndTenantId(Long id, String tenantId);
}
