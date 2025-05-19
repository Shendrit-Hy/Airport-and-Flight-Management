package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.dto.FlightDTO;
import com.mbi_re.airport_management.model.Flight;
import com.mbi_re.airport_management.model.Seat;
import com.mbi_re.airport_management.repository.FlightRepository;
import com.mbi_re.airport_management.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FlightService {

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private SeatRepository seatRepository;

    public List<FlightDTO> getTodayAndUpcomingFlights(String tenantId) {
        LocalDate today = LocalDate.now();
        List<Flight> flights = flightRepository.findByFlightDateGreaterThanEqualAndTenantId(today, tenantId);

        return flights.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<FlightDTO> getAllFlights(String tenantId) {
        List<Flight> flights = flightRepository.findByTenantId(tenantId);

        return flights.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public FlightDTO addFlight(FlightDTO dto) {
        Flight flight = mapToEntity(dto);
        flight.setTenantId(dto.getTenantId()); // ensure tenant ID is set

        // Save the flight first
        Flight saved = flightRepository.save(flight);

        // Auto-generate seats
        for (int i = 1; i <= saved.getAvailableSeat(); i++) {
            Seat seat = new Seat();
            seat.setSeatNumber("A" + i);
            seat.setBooked(false);
            seat.setFlight(saved);
            seat.setTenantId(saved.getTenantId());
            seatRepository.save(seat);
        }

        return mapToDTO(saved);
    }

    private FlightDTO mapToDTO(Flight flight) {
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
        dto.setAirline(flight.getAirline());
        dto.setTenantId(flight.getTenantId());
        return dto;
    }

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
        flight.setAirline(dto.getAirline());
        flight.setTenantId(dto.getTenantId());
        return flight;
    }

    public void deleteFlight(Long flightId, String tenantId) {
        Flight flight = flightRepository.findByIdAndTenantId(flightId, tenantId)
                .orElseThrow(() -> new RuntimeException("Flight not found or access denied"));
        flightRepository.delete(flight);
    }
}