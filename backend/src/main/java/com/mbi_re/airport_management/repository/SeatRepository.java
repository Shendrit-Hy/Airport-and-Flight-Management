package com.mbi_re.airport_management.repository;

import com.mbi_re.airport_management.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findByFlightIdAndTenantId(Long flightId, String tenantId);
    List<Seat> findByFlightIdAndTenantIdAndBookedFalse(Long flightId, String tenantId);
    Optional<Seat> findByIdAndTenantId(Long id, String tenantId);

}
