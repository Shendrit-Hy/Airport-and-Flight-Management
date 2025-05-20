package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.config.TenantContext;
import com.mbi_re.airport_management.dto.BookingDTO;
import com.mbi_re.airport_management.model.Booking;
import com.mbi_re.airport_management.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service

public class BookingService {

    private final BookingRepository repository;

    public BookingService(BookingRepository repository) {
        this.repository = repository;
    }

    public Booking createBooking(BookingDTO dto, String tenantId) {
        Booking booking = new Booking();

        booking.setFlightNumber(dto.getFlightNumber());
        booking.setPassengerName(dto.getPassengerName());
        booking.setSeatNumber(String.join(",", dto.getSeatNumber()));  // Convert list to comma-separated
        booking.setPassengerId(dto.getPassengerId());

        booking.setBookingTime(LocalDateTime.now());
        booking.setStatus("PAID");
        booking.setBookingId(generateBookingId());
        booking.setTenantId(tenantId);

        // Optional: store tenant ID in the entity if needed

        return repository.save(booking);
    }

    public List<Booking> getAllBookings() {
        String tenantId = TenantContext.getTenantId();
        return repository.findByTenantId(tenantId);

    }

    public Booking getBookingById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Booking updateBooking(Long id, BookingDTO dto) {
        return repository.findById(id).map(b -> {
            b.setPassengerName(dto.getPassengerName());
            b.setPassengerId(dto.getPassengerId());
            b.setBookingId(dto.getBookingId());
            b.setFlightNumber(dto.getFlightNumber());
            b.setSeatNumber(dto.getSeatNumber());
            b.setStatus(dto.getStatus());
            return repository.save(b);
        }).orElse(null);
    }

    private String generateBookingId() {
        return "BOOK-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public void deleteBooking(Long id) {
        repository.deleteById(id);
    }
}

