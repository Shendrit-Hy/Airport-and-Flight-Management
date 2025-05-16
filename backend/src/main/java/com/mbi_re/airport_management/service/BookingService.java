package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.config.TenantContext;
import com.mbi_re.airport_management.dto.BookingDTO;
import com.mbi_re.airport_management.model.Booking;
import com.mbi_re.airport_management.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service

public class BookingService {

    private final BookingRepository repository;

    public BookingService(BookingRepository repository) {
        this.repository = repository;
    }

    public Booking createBooking(BookingDTO dto, String tenantId) {
        Booking booking = new Booking();
        booking.setPassengerName(dto.getPassengerName());
        booking.setFlightNumber(dto.getFlightNumber());
        booking.setSeatNumber(dto.getSeatNumber());
        booking.setStatus(dto.getStatus() != null ? dto.getStatus() : "BOOKED");
        booking.setBookingTime(LocalDateTime.now());
        booking.setTenantId(tenantId);

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
            b.setFlightNumber(dto.getFlightNumber());
            b.setSeatNumber(dto.getSeatNumber());
            b.setStatus(dto.getStatus());
            return repository.save(b);
        }).orElse(null);
    }

    public void deleteBooking(Long id) {
        repository.deleteById(id);
    }
}

