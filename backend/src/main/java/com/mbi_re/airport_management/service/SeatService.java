package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.dto.SeatDTO;
import com.mbi_re.airport_management.model.Seat;
import com.mbi_re.airport_management.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for managing seat-related operations such as availability checks and booking.
 */
@Service
@RequiredArgsConstructor
public class SeatService {

    @Autowired
    private SeatRepository seatRepository;

    /**
     * Retrieves all available (unbooked) seats for a given flight and tenant.
     * This data is cacheable as availability is frequently queried.
     *
     * @param flightId the ID of the flight
     * @param tenantId the tenant ID
     * @return list of available seats
     */
    @Cacheable(value = "availableSeats", key = "#flightId + '_' + #tenantId")
    public List<SeatDTO> getAvailableSeats(Long flightId, String tenantId) {
        List<Seat> seats = seatRepository.findByFlightIdAndTenantIdAndBookedFalse(flightId, tenantId);
        return seats.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    /**
     * Retrieves all seats for a specific flight and tenant.
     *
     * @param flightId the ID of the flight
     * @param tenantId the tenant ID
     * @return list of all seats for the flight
     */
    public List<SeatDTO> getAllSeats(Long flightId, String tenantId) {
        List<Seat> seats = seatRepository.findByFlightIdAndTenantId(flightId, tenantId);
        return seats.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    /**
     * Marks a specific seat as unavailable (booked) for a tenant.
     *
     * @param seatId   the ID of the seat
     * @param tenantId the tenant ID
     * @return the updated SeatDTO with availability set to false
     */
    public SeatDTO markSeatAsUnavailable(Long seatId, String tenantId) {
        Seat seat = seatRepository.findByIdAndTenantId(seatId, tenantId)
                .orElseThrow(() -> new RuntimeException("Seat not found"));
        seat.setBooked(true);
        Seat updatedSeat = seatRepository.save(seat);
        return convertToDTO(updatedSeat);
    }

    /**
     * Converts a Seat entity into a SeatDTO.
     *
     * @param seat the Seat entity
     * @return SeatDTO
     */
    private SeatDTO convertToDTO(Seat seat) {
        return new SeatDTO(
                seat.getId(),
                seat.getSeatNumber(),
                seat.getFlight().getId(),
                seat.getFlight().getFlightNumber(),
                seat.getTenantId(),
                seat.isBooked()
        );
    }
}
