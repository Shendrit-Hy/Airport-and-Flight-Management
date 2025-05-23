package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.dto.SeatDTO;
import com.mbi_re.airport_management.model.Flight;
import com.mbi_re.airport_management.model.Seat;
import com.mbi_re.airport_management.repository.SeatRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SeatServiceTest {

    @Mock
    private SeatRepository seatRepository;

    @InjectMocks
    private SeatService seatService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAvailableSeats() {
        Seat seat1 = getSampleSeat(false);
        Seat seat2 = getSampleSeat(false);
        seat2.setId(2L);
        seat2.setSeatNumber("2B");

        when(seatRepository.findByFlightIdAndTenantIdAndBookedFalse(1L, "tenantA"))
                .thenReturn(List.of(seat1, seat2));

        List<SeatDTO> availableSeats = seatService.getAvailableSeats(1L, "tenantA");

        assertEquals(2, availableSeats.size());
        assertFalse(availableSeats.get(0).isBooked());
        verify(seatRepository).findByFlightIdAndTenantIdAndBookedFalse(1L, "tenantA");
    }

    @Test
    void testGetAllSeats() {
        Seat seat = getSampleSeat(true);

        when(seatRepository.findByFlightIdAndTenantId(1L, "tenantA")).thenReturn(List.of(seat));

        List<SeatDTO> seats = seatService.getAllSeats(1L, "tenantA");

        assertEquals(1, seats.size());
        assertEquals("1A", seats.get(0).getSeatNumber());
        verify(seatRepository).findByFlightIdAndTenantId(1L, "tenantA");
    }

    @Test
    void testMarkSeatAsUnavailable_Success() {
        Seat seat = getSampleSeat(false);
        Seat updatedSeat = getSampleSeat(true); // same seat but booked

        when(seatRepository.findByIdAndTenantId(1L, "tenantA")).thenReturn(Optional.of(seat));
        when(seatRepository.save(any(Seat.class))).thenReturn(updatedSeat);

        SeatDTO result = seatService.markSeatAsUnavailable(1L, "tenantA");

        assertTrue(result.isBooked());
        assertEquals("1A", result.getSeatNumber());
        verify(seatRepository).save(seat);
    }

    @Test
    void testMarkSeatAsUnavailable_NotFound() {
        when(seatRepository.findByIdAndTenantId(99L, "tenantA")).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                seatService.markSeatAsUnavailable(99L, "tenantA"));

        assertEquals("Seat not found", ex.getMessage());
    }


    private Seat getSampleSeat(boolean booked) {
        Seat seat = new Seat();
        seat.setId(1L);
        seat.setSeatNumber("1A");
        seat.setBooked(booked);
        seat.setTenantId("tenantA");

        Flight flight = new Flight();
        flight.setId(1L);
        flight.setFlightNumber("FL123");
        seat.setFlight(flight);

        return seat;
    }
}
