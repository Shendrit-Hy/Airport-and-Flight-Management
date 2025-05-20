package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.dto.FlightDTO;
import com.mbi_re.airport_management.model.Flight;
import com.mbi_re.airport_management.model.Seat;
import com.mbi_re.airport_management.repository.FlightRepository;
import com.mbi_re.airport_management.repository.GateRepository;
import com.mbi_re.airport_management.repository.SeatRepository;
import com.mbi_re.airport_management.repository.TerminalRepository;
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

    @Autowired
    private GateRepository gateRepository;

    @Autowired
    private TerminalRepository terminalRepository;


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
        int totalSeats = saved.getAvailableSeat();
        int seatsPerRow = 6; // You can adjust this number
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

        if (flight.getGate() != null) {
            dto.setGateId(flight.getGate().getId());
        }
        if (flight.getTerminal() != null) {
            dto.setTerminalId(flight.getTerminal().getId());
        }

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


    public void deleteFlight(Long flightId, String tenantId) {
        Flight flight = flightRepository.findByIdAndTenantId(flightId, tenantId)
                .orElseThrow(() -> new RuntimeException("Flight not found or access denied"));
        flightRepository.delete(flight);
    }
}