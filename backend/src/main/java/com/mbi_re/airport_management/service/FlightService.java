package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.dto.FlightDTO;
import com.mbi_re.airport_management.model.Flight;
import com.mbi_re.airport_management.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FlightService {

    @Autowired
    private FlightRepository flightRepository;

    public FlightDTO createFlight(FlightDTO dto) {
        Flight flight = dtoToEntity(dto);
        return entityToDto(flightRepository.save(flight));
    }

    public List<FlightDTO> getAllFlights() {
        return flightRepository.findAll().stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    public FlightDTO getFlightById(Long id) {
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Flight not found"));
        return entityToDto(flight);
    }

    public FlightDTO updateFlight(Long id, FlightDTO dto) {
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Flight not found"));

        flight.setFlightNumber(dto.getFlightNumber());
        flight.setDepartureAirport(dto.getDepartureAirport());
        flight.setArrivalAirport(dto.getArrivalAirport());
        flight.setDepartureTime(dto.getDepartureTime());
        flight.setArrivalTime(dto.getArrivalTime());
        flight.setStatus(dto.getStatus());

        return entityToDto(flightRepository.save(flight));
    }

    public void deleteFlight(Long id) {
        flightRepository.deleteById(id);
    }

    // Helper methods
    private Flight dtoToEntity(FlightDTO dto) {
        return new Flight(
                dto.getId(),
                dto.getFlightNumber(),
                dto.getDepartureAirport(),
                dto.getArrivalAirport(),
                dto.getDepartureTime(),
                dto.getArrivalTime(),
                dto.getStatus()
        );
    }

    private FlightDTO entityToDto(Flight flight) {
        return new FlightDTO(
                flight.getId(),
                flight.getFlightNumber(),
                flight.getDepartureAirport(),
                flight.getArrivalAirport(),
                flight.getDepartureTime(),
                flight.getArrivalTime(),
                flight.getStatus()
        );
    }
    public String getFlightStatus(Long flightId) {
        Flight flight = flightRepository.findById(flightId)
                .orElseThrow(() -> new RuntimeException("Flight not found"));
        return flight.getStatus();
    }

    public List<FlightDTO> getLiveFlights() {
        return flightRepository.findByStatus("ACTIVE").stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }


}
