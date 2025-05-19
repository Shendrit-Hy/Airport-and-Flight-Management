package com.mbi_re.airport_management.repository;

import com.mbi_re.airport_management.model.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {
    List<Passenger> findAllByTenantId(String tenantId);
}

