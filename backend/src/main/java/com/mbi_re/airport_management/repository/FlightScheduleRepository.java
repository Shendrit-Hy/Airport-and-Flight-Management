package com.mbi_re.airport_management.repository;

import com.mbi_re.airport_management.model.FlightSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FlightScheduleRepository extends JpaRepository<FlightSchedule, Long> {
    List<FlightSchedule> findByTenantId(String tenantId);
}
