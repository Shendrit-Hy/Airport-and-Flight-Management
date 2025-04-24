package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.dto.BookingDTO;
import com.mbi_re.airport_management.model.Booking;
import com.mbi_re.airport_management.repository.BookingRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingService {

    private final BookingRepository repository;

    public BookingService(BookingRepository repository) {
        this.repository = repository;
    }

    public Booking createBooking(BookingDTO dto) {
        Booking booking = Booking.builder()
                .passengerName(dto.getPassengerName())
                .flightNumber(dto.getFlightNumber())
                .seatNumber(dto.getSeatNumber())
                .status(dto.getStatus() != null ? dto.getStatus() : "BOOKED")
                .bookingTime(LocalDateTime.now())
                .build();
        return repository.save(booking);
    }

    public List<Booking> getAllBookings() {
        return repository.findAll();
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

