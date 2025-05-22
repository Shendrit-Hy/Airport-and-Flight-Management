package com.mbi_re.airport_management.repository;

import com.mbi_re.airport_management.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Seat entities, scoped by tenant.
 */
public interface SeatRepository extends JpaRepository<Seat, Long> {

    /**
     * Finds all seats for a flight and tenant.
     *
     * @param flightId the flight ID
     * @param tenantId the tenant ID
     * @return list of seats
     */
    List<Seat> findByFlightIdAndTenantId(Long flightId, String tenantId);

    /**
     * Finds unbooked (available) seats for a flight and tenant.
     *
     * @param flightId the flight ID
     * @param tenantId the tenant ID
     * @return list of available seats
     */
    List<Seat> findByFlightIdAndTenantIdAndBookedFalse(Long flightId, String tenantId);

    /**
     * Finds a seat by ID scoped to the given tenant.
     *
     * @param id       the seat ID
     * @param tenantId the tenant ID
     * @return optional seat
     */
    Optional<Seat> findByIdAndTenantId(Long id, String tenantId);

    void deleteByFlightIdAndTenantId(Long flightId, String tenantId);
}
