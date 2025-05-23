package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.config.TenantContext;
import com.mbi_re.airport_management.dto.BookingDTO;
import com.mbi_re.airport_management.model.Booking;
import com.mbi_re.airport_management.repository.BookingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookingServiceTest {

    private BookingService bookingService;
    private BookingRepository bookingRepository;

    @BeforeEach
    void setUp() {
        bookingRepository = mock(BookingRepository.class);
        bookingService = new BookingService(bookingRepository);
    }

    @Test
    void testCreateBooking() {
        BookingDTO dto = new BookingDTO();
        dto.setFlightNumber("LH123");
        dto.setPassengerName("John Doe");
        dto.setSeatNumber("A1");

        Booking savedBooking = new Booking();
        savedBooking.setId(1L);
        savedBooking.setFlightNumber("LH123");
        savedBooking.setPassengerName("John Doe");

        when(bookingRepository.save(any(Booking.class))).thenReturn(savedBooking);

        Booking result = bookingService.createBooking(dto, "tenant1");

        assertNotNull(result);
        assertEquals("LH123", result.getFlightNumber());
        assertEquals("John Doe", result.getPassengerName());
        verify(bookingRepository).save(any(Booking.class));
    }

    @Test
    void testGetAllBookings() {
        try (MockedStatic<TenantContext> mocked = mockStatic(TenantContext.class)) {
            mocked.when(TenantContext::getTenantId).thenReturn("tenant2");

            List<Booking> bookings = List.of(new Booking(), new Booking());
            when(bookingRepository.findByTenantId("tenant2")).thenReturn(bookings);

            List<Booking> result = bookingService.getAllBookings();

            assertEquals(2, result.size());
            verify(bookingRepository).findByTenantId("tenant2");
        }
    }

    @Test
    void testGetBookingById_Found() {
        try (MockedStatic<TenantContext> mocked = mockStatic(TenantContext.class)) {
            mocked.when(TenantContext::getTenantId).thenReturn("tenant3");

            Booking booking = new Booking();
            booking.setId(42L);
            booking.setTenantId("tenant3");

            when(bookingRepository.findByIdAndTenantId(42L, "tenant3")).thenReturn(Optional.of(booking));

            Booking result = bookingService.getBookingById(42L);

            assertNotNull(result);
            assertEquals(42L, result.getId());
        }
    }

    @Test
    void testGetBookingById_NotFound() {
        try (MockedStatic<TenantContext> mocked = mockStatic(TenantContext.class)) {
            mocked.when(TenantContext::getTenantId).thenReturn("tenantX");

            when(bookingRepository.findByIdAndTenantId(99L, "tenantX")).thenReturn(Optional.empty());

            Booking result = bookingService.getBookingById(99L);

            assertNull(result);
        }
    }

    @Test
    void testUpdateBooking_Found() {
        try (MockedStatic<TenantContext> mocked = mockStatic(TenantContext.class)) {
            mocked.when(TenantContext::getTenantId).thenReturn("tenant4");

            Booking existing = new Booking();
            existing.setId(55L);
            existing.setTenantId("tenant4");

            when(bookingRepository.findByIdAndTenantId(55L, "tenant4")).thenReturn(Optional.of(existing));
            when(bookingRepository.save(any(Booking.class))).thenAnswer(invocation -> invocation.getArgument(0));

            BookingDTO dto = new BookingDTO();
            dto.setBookingId("BID123");
            dto.setPassengerName("Alice");
            dto.setFlightNumber("AZ123");
            dto.setSeatNumber("B2");
            dto.setStatus("CONFIRMED");

            Booking result = bookingService.updateBooking(55L, dto);

            assertNotNull(result);
            assertEquals("Alice", result.getPassengerName());
            assertEquals("CONFIRMED", result.getStatus());
        }
    }

    @Test
    void testUpdateBooking_NotFound() {
        try (MockedStatic<TenantContext> mocked = mockStatic(TenantContext.class)) {
            mocked.when(TenantContext::getTenantId).thenReturn("tenant5");

            when(bookingRepository.findByIdAndTenantId(100L, "tenant5")).thenReturn(Optional.empty());

            BookingDTO dto = new BookingDTO();
            Booking result = bookingService.updateBooking(100L, dto);

            assertNull(result);
        }
    }

    @Test
    void testDeleteBooking_Found() {
        try (MockedStatic<TenantContext> mocked = mockStatic(TenantContext.class)) {
            mocked.when(TenantContext::getTenantId).thenReturn("tenant6");

            Booking booking = new Booking();
            booking.setId(77L);
            booking.setTenantId("tenant6");

            when(bookingRepository.findByIdAndTenantId(77L, "tenant6")).thenReturn(Optional.of(booking));

            bookingService.deleteBooking(77L);

            verify(bookingRepository).delete(booking);
        }
    }

    @Test
    void testDeleteBooking_NotFound() {
        try (MockedStatic<TenantContext> mocked = mockStatic(TenantContext.class)) {
            mocked.when(TenantContext::getTenantId).thenReturn("tenant7");

            when(bookingRepository.findByIdAndTenantId(888L, "tenant7")).thenReturn(Optional.empty());

            bookingService.deleteBooking(888L);

            verify(bookingRepository, never()).delete(any());
        }
    }
}
