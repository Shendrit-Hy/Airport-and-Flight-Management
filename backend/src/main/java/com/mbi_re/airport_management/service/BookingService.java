package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.config.TenantContext;
import com.mbi_re.airport_management.dto.BookingDTO;
import com.mbi_re.airport_management.model.Booking;
import com.mbi_re.airport_management.repository.BookingRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookingService {

    private final BookingRepository repository;

    public BookingService(BookingRepository repository) {
        this.repository = repository;
    }

    /**
     * Creates a new booking for the specified tenant.
     * Evicts the bookings cache for the tenant to maintain cache consistency.
     *
     * @param dto      the booking data transfer object containing booking details
     * @param tenantId the tenant identifier to associate the booking with
     * @return the created Booking entity
     */
    @CacheEvict(value = "bookings", key = "#tenantId + '_all'")
    public Booking createBooking(BookingDTO dto, String tenantId) {
        Booking booking = new Booking();
        booking.setFlightNumber(dto.getFlightNumber());
        booking.setPassengerName(dto.getPassengerName());
        booking.setSeatNumber(String.join(",", dto.getSeatNumber()));
        booking.setPassengerId(dto.getPassengerId());
        booking.setBookingTime(LocalDateTime.now());
        booking.setStatus("PAID");
        booking.setBookingId(generateBookingId());
        booking.setTenantId(tenantId);

        return repository.save(booking);
    }

    /**
     * Retrieves all bookings for the current tenant.
     * Results are cached for performance improvement.
     *
     * @return list of Booking entities associated with the current tenant
     */
    @Cacheable(value = "bookings", key = "T(com.mbi_re.airport_management.config.TenantContext).getTenantId() + '_all'")
    public List<Booking> getAllBookings() {
        String tenantId = TenantContext.getTenantId();
        return repository.findByTenantId(tenantId);
    }

    /**
     * Retrieves a booking by its ID for the current tenant.
     * Returns null if the booking is not found or does not belong to the tenant.
     *
     * @param id the booking ID
     * @return the Booking entity if found and tenant matches, else null
     */
    public Booking getBookingById(Long id) {
        String tenantId = TenantContext.getTenantId();
        return repository.findByIdAndTenantId(id, tenantId).orElse(null);
    }

    /**
     * Updates an existing booking for the current tenant by booking ID.
     * Evicts the bookings cache for the tenant after update.
     *
     * @param id  the booking ID to update
     * @param dto the booking data transfer object containing updated fields
     * @return the updated Booking entity, or null if not found or unauthorized
     */
    @CacheEvict(value = "bookings", key = "T(com.mbi_re.airport_management.config.TenantContext).getTenantId() + '_all'")
    public Booking updateBooking(Long id, BookingDTO dto) {
        String tenantId = TenantContext.getTenantId();
        Optional<Booking> optional = repository.findByIdAndTenantId(id, tenantId);

        return optional.map(b -> {
            b.setPassengerName(dto.getPassengerName());
            b.setPassengerId(dto.getPassengerId());
            b.setBookingId(dto.getBookingId());
            b.setFlightNumber(dto.getFlightNumber());
            b.setSeatNumber(dto.getSeatNumber());
            b.setStatus(dto.getStatus());
            return repository.save(b);
        }).orElse(null);
    }

    /**
     * Deletes a booking by ID scoped to the current tenant.
     * Evicts the bookings cache for the tenant upon successful deletion.
     * The operation is transactional.
     *
     * @param id the booking ID to delete
     */
    @CacheEvict(value = "bookings", key = "T(com.mbi_re.airport_management.config.TenantContext).getTenantId() + '_all'")
    @Transactional
    public void deleteBooking(Long id) {
        String tenantId = TenantContext.getTenantId();
        Optional<Booking> optional = repository.findByIdAndTenantId(id, tenantId);
        optional.ifPresent(repository::delete);
    }

    /**
     * Updates the check-in status of a booking for the current tenant.
     *
     * @param id        the booking ID
     * @param checkedIn boolean flag indicating if the passenger has checked in
     * @return Optional containing the updated Booking if found, else empty
     */
    public Optional<Booking> updateCheckInStatus(Long id, boolean checkedIn) {
        String tenantId = TenantContext.getTenantId();
        return repository.findByIdAndTenantId(id, tenantId).map(booking -> {
            booking.setCheckedIn(checkedIn);
            return repository.save(booking);
        });
    }

    /**
     * Generates a unique booking ID string.
     * The format is "BOOK-" followed by an 8-character uppercase UUID substring.
     *
     * @return the generated unique booking ID string
     */
    private String generateBookingId() {
        return "BOOK-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

}
