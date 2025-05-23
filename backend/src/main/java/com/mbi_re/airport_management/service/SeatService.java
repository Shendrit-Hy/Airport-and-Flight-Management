package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.dto.SeatDTO;
import com.mbi_re.airport_management.model.Seat;
import com.mbi_re.airport_management.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
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
     * Retrieves all available (unbooked) seats for a specific flight and tenant.
     * <p>
     * This method evicts the cache entry for available seats of the given flight and tenant,
     * ensuring that stale cache entries are removed after availability changes.
     * </p>
     *
     * @param flightId the ID of the flight to fetch available seats for
     * @param tenantId the tenant ID to scope the seats
     * @return list of {@link SeatDTO} representing available seats
     */
    @CacheEvict(value = "availableSeats", key = "#flightId + '_' + #tenantId")
    public List<SeatDTO> getAvailableSeats(Long flightId, String tenantId) {
        List<Seat> seats = seatRepository.findByFlightIdAndTenantIdAndBookedFalse(flightId, tenantId);
        return seats.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    /**
     * Retrieves all seats (both booked and available) for a specific flight and tenant.
     *
     * @param flightId the ID of the flight to fetch seats for
     * @param tenantId the tenant ID to scope the seats
     * @return list of {@link SeatDTO} representing all seats for the flight
     */
    public List<SeatDTO> getAllSeats(Long flightId, String tenantId) {
        List<Seat> seats = seatRepository.findByFlightIdAndTenantId(flightId, tenantId);
        return seats.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    /**
     * Marks a specific seat as booked (unavailable) for a tenant.
     * <p>
     * If the seat with the given ID and tenant does not exist, an exception is thrown.
     * </p>
     *
     * @param seatId   the ID of the seat to mark as booked
     * @param tenantId the tenant ID for scoping
     * @return updated {@link SeatDTO} with the booked status set to true
     * @throws RuntimeException if the seat is not found for the given tenant
     */
    public SeatDTO markSeatAsUnavailable(Long seatId, String tenantId) {
        Seat seat = seatRepository.findByIdAndTenantId(seatId, tenantId)
                .orElseThrow(() -> new RuntimeException("Seat not found"));
        seat.setBooked(true);
        Seat updatedSeat = seatRepository.save(seat);
        return convertToDTO(updatedSeat);
    }

    /**
     * Converts a {@link Seat} entity into a {@link SeatDTO}.
     *
     * @param seat the {@link Seat} entity to convert
     * @return the corresponding {@link SeatDTO}
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
