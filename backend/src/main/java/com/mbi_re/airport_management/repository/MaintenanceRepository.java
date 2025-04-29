package com.mbi_re.airport_management.repository;

import com.mbi_re.airport_management.model.Maintenance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MaintenanceRepository extends JpaRepository<Maintenance, Long> {
    List<Maintenance> findByAirportCodeAndStatus(String airportCode, String status);
    List<Maintenance> findByAirportCode(String airportCode);
    List<Maintenance> findByStatus(String status);
}

