package com.mbi_re.airport_management.repository;

import com.mbi_re.airport_management.model.Maintenance;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MaintenanceRepository extends JpaRepository<Maintenance, Long> {
    List<Maintenance> findByAirportIDAndStatusAndTenantId(String airportID, String status, String tenantId);
    List<Maintenance> findByAirportIDAndTenantId(String airportID, String tenantId);
    List<Maintenance> findByStatusAndTenantId(String status, String tenantId);
    List<Maintenance> findAllByTenantId(String tenantId);
}
