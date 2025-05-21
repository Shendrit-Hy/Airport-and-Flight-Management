package com.mbi_re.airport_management.repository;

import com.mbi_re.airport_management.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    /**
     * Retrieves all bookings for a given tenant ID.
     *
     * @param tenantId the tenant identifier
     * @return list of bookings belonging to the tenant
     */
    List<Booking> findByTenantId(String tenantId);

    /**
     * Finds a booking by ID and tenant ID.
     *
     * @param id the booking ID
     * @param tenantId the tenant identifier
     * @return an optional booking belonging to the tenant
     */
    Optional<Booking> findByIdAndTenantId(Long id, String tenantId);
}
