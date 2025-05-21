package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.config.TenantContext;
import com.mbi_re.airport_management.dto.BookingDTO;
import com.mbi_re.airport_management.model.Booking;
import com.mbi_re.airport_management.repository.BookingRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

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
     * Creates a new booking for a tenant.
     *
     * @param dto booking data transfer object
     * @param tenantId the tenant identifier
     * @return the created booking
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
     *
     * @return list of bookings
     */
    @Cacheable(value = "bookings", key = "T(com.mbi_re.airport_management.config.TenantContext).getTenantId() + '_all'")
    public List<Booking> getAllBookings() {
        String tenantId = TenantContext.getTenantId();
        return repository.findByTenantId(tenantId);
    }

    /**
     * Retrieves a booking by ID, validated for current tenant.
     *
     * @param id booking ID
     * @return the booking, or null if not found or mismatched tenant
     */
    public Booking getBookingById(Long id) {
        String tenantId = TenantContext.getTenantId();
        return repository.findByIdAndTenantId(id, tenantId).orElse(null);
    }

    /**
     * Updates an existing booking by ID.
     *
     * @param id booking ID
     * @param dto booking data
     * @return updated booking or null if not found or unauthorized
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
     * Deletes a booking by ID, tenant-scoped.
     *
     * @param id booking ID
     */
    @CacheEvict(value = "bookings", key = "T(com.mbi_re.airport_management.config.TenantContext).getTenantId() + '_all'")
    public void deleteBooking(Long id) {
        String tenantId = TenantContext.getTenantId();
        Optional<Booking> optional = repository.findByIdAndTenantId(id, tenantId);
        optional.ifPresent(repository::delete);
    }

    /**
     * Generates a unique booking ID string.
     *
     * @return generated booking ID
     */
    private String generateBookingId() {
        return "BOOK-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
