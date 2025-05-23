package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.dto.FlightDTO;
import com.mbi_re.airport_management.model.Airline;
import com.mbi_re.airport_management.model.Flight;
import com.mbi_re.airport_management.model.Seat;
import com.mbi_re.airport_management.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for managing flight-related operations.
 * Handles creation, deletion, and retrieval of tenant-scoped flights.
 */
@Service
@RequiredArgsConstructor
public class FlightService {

    @Autowired
    private FlightRepository flightRepository;
    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private GateRepository gateRepository;
    @Autowired
    private TerminalRepository terminalRepository;
    @Autowired
    private AirlineRepository airlineRepository;

    /**
     * Retrieves today's and upcoming flights for a given tenant.
     *
     * @param tenantId the tenant identifier
     * @return a list of {@link FlightDTO} representing today's and upcoming flights
     */
    @Cacheable(value = "flights_today_upcoming", key = "#tenantId")
    public List<FlightDTO> getTodayAndUpcomingFlights(String tenantId) {
        LocalDate today = LocalDate.now();
        return flightRepository.findByFlightDateGreaterThanEqualAndTenantId(today, tenantId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves all flights for the authenticated tenant.
     *
     * @param tenantId the tenant identifier
     * @return a list of {@link FlightDTO} for all flights of the tenant
     */
    @Cacheable(value = "flights_all", key = "#tenantId")
    public List<FlightDTO> getAllFlights(String tenantId) {
        return flightRepository.findByTenantId(tenantId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Adds a new flight and automatically generates seats for it.
     * Evicts relevant flight caches for the tenant.
     *
     * @param dto the flight data transfer object to add
     * @return the created flight as a {@link FlightDTO}
     */
    @CacheEvict(value = { "flights_today_upcoming", "flights_all" }, key = "#dto.tenantId")
    public FlightDTO addFlight(FlightDTO dto) {
        Flight flight = mapToEntity(dto);
        flight.setTenantId(dto.getTenantId());
        Flight saved = flightRepository.save(flight);

        generateSeats(saved);
        return mapToDTO(saved);
    }

    /**
     * Deletes a flight and all associated seats for the specified tenant.
     * Evicts relevant flight caches after deletion.
     *
     * @param flightId the ID of the flight to delete
     * @param tenantId the tenant identifier
     * @throws RuntimeException if the flight is not found or tenant access is denied
     */
    @Transactional
    @CacheEvict(value = { "flights_today_upcoming", "flights_all" }, key = "#tenantId")
    public void deleteFlight(Long flightId, String tenantId) {
        Flight flight = flightRepository.findByIdAndTenantId(flightId, tenantId)
                .orElseThrow(() -> new RuntimeException("Flight not found or access denied"));

        // Delete all seats associated with the flight
        seatRepository.deleteByFlightIdAndTenantId(flightId, tenantId);

        // Delete the flight
        flightRepository.delete(flight);
    }

    /**
     * Maps a {@link Flight} entity to its DTO representation.
     *
     * @param flight the flight entity
     * @return a {@link FlightDTO} representing the flight data
     */
    public FlightDTO mapToDTO(Flight flight) {
        FlightDTO dto = new FlightDTO();
        dto.setId(flight.getId());
        dto.setFlightNumber(flight.getFlightNumber());
        dto.setDepartureAirport(flight.getDepartureAirport());
        dto.setArrivalAirport(flight.getArrivalAirport());
        dto.setDepartureTime(flight.getDepartureTime());
        dto.setArrivalTime(flight.getArrivalTime());
        dto.setFlightDate(flight.getFlightDate());
        dto.setAvailableSeat(flight.getAvailableSeat());
        dto.setPrice(flight.getPrice());
        dto.setFlightStatus(flight.getFlightStatus());
        dto.setTenantId(flight.getTenantId());

        if (flight.getAirline() != null) dto.setAirlineId(flight.getAirline().getId());
        if (flight.getGate() != null) dto.setGateId(flight.getGate().getId());
        if (flight.getTerminal() != null) dto.setTerminalId(flight.getTerminal().getId());

        return dto;
    }

    /**
     * Converts a {@link FlightDTO} to its entity representation.
     *
     * @param dto the flight DTO
     * @return the corresponding {@link Flight} entity
     * @throws RuntimeException if related Airline, Gate, or Terminal is not found for the tenant
     */
    private Flight mapToEntity(FlightDTO dto) {
        Flight flight = new Flight();
        flight.setFlightNumber(dto.getFlightNumber());
        flight.setDepartureAirport(dto.getDepartureAirport());
        flight.setArrivalAirport(dto.getArrivalAirport());
        flight.setDepartureTime(dto.getDepartureTime());
        flight.setArrivalTime(dto.getArrivalTime());
        flight.setFlightDate(dto.getFlightDate());
        flight.setAvailableSeat(dto.getAvailableSeat());
        flight.setPrice(dto.getPrice());
        flight.setFlightStatus(dto.getFlightStatus());
        flight.setTenantId(dto.getTenantId());

        if (dto.getAirlineId() != null) {
            Airline airline = airlineRepository.findByIdAndTenantId(dto.getAirlineId(), dto.getTenantId())
                    .orElseThrow(() -> new RuntimeException("Airline not found with id: " + dto.getAirlineId()));
            flight.setAirline(airline);
        }

        if (dto.getGateId() != null) {
            flight.setGate(gateRepository.findByIdAndTenantId(dto.getGateId(), dto.getTenantId())
                    .orElseThrow(() -> new RuntimeException("Gate not found")));
        }

        if (dto.getTerminalId() != null) {
            flight.setTerminal(terminalRepository.findByIdAndTenantId(dto.getTerminalId(), dto.getTenantId())
                    .orElseThrow(() -> new RuntimeException("Terminal not found")));
        }

        return flight;
    }

    /**
     * Auto-generates seats for a newly created flight.
     * Seats are labeled in rows (A, B, C, ...) and columns (1 to 6).
     *
     * @param saved the flight entity for which seats are generated
     */
    private void generateSeats(Flight saved) {
        int totalSeats = saved.getAvailableSeat();
        int seatsPerRow = 6;
        char row = 'A';
        int seatCount = 0;

        while (seatCount < totalSeats) {
            for (int col = 1; col <= seatsPerRow && seatCount < totalSeats; col++) {
                Seat seat = new Seat();
                seat.setSeatNumber("" + row + col);
                seat.setBooked(false);
                seat.setFlight(saved);
                seat.setTenantId(saved.getTenantId());
                seatRepository.save(seat);
                seatCount++;
            }
            row++;
        }
    }
}
