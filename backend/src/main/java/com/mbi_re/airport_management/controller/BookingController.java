package com.mbi_re.airport_management.controller;

import com.mbi_re.airport_management.dto.BookingDTO;
import com.mbi_re.airport_management.model.Booking;
import com.mbi_re.airport_management.service.BookingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin
public class BookingController {

    private final BookingService service;

    public BookingController(BookingService service) {
        this.service = service;
    }

    @PostMapping
    public Booking createBooking(@RequestBody BookingDTO dto) {
        return service.createBooking(dto);
    }

    @GetMapping
    public List<Booking> getAll() {
        return service.getAllBookings();
    }

    @GetMapping("/{id}")
    public Booking getById(@PathVariable Long id) {
        return service.getBookingById(id);
    }

    @PutMapping("/{id}")
    public Booking update(@PathVariable Long id, @RequestBody BookingDTO dto) {
        return service.updateBooking(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteBooking(id);
    }
}

