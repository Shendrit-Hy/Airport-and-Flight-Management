package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.dto.FlightDTO;
import com.mbi_re.airport_management.model.Flight;
import com.mbi_re.airport_management.repository.FlightRepository;
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

    public List<FlightDTO> getTodayAndUpcomingFlights() {
        LocalDate today = LocalDate.now();
        List<Flight> flights = flightRepository.findByFlightDateGreaterThanEqual(today);

        return flights.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
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
        dto.setAirline(flight.getAirline());
        return dto;
    }

    public FlightDTO addFlight(FlightDTO dto) {
        Flight flight = new Flight();
        flight.setFlightNumber(dto.getFlightNumber());
        flight.setDepartureAirport(dto.getDepartureAirport());
        flight.setArrivalAirport(dto.getArrivalAirport());
        flight.setDepartureTime(dto.getDepartureTime());
        flight.setArrivalTime(dto.getArrivalTime());
        flight.setFlightDate(dto.getFlightDate());
        flight.setAvailableSeat(dto.getAvailableSeat());
        flight.setPrice(dto.getPrice());
        flight.setAirline(dto.getAirline());

        Flight saved = flightRepository.save(flight);

        FlightDTO result = new FlightDTO();
        result.setId(saved.getId());
        result.setFlightNumber(saved.getFlightNumber());
        result.setDepartureAirport(saved.getDepartureAirport());
        result.setArrivalAirport(saved.getArrivalAirport());
        result.setDepartureTime(saved.getDepartureTime());
        result.setArrivalTime(saved.getArrivalTime());
        result.setFlightDate(saved.getFlightDate());
        result.setAvailableSeat(saved.getAvailableSeat());
        result.setPrice(saved.getPrice());
        result.setAirline(saved.getAirline());

        return result;
    }
}
