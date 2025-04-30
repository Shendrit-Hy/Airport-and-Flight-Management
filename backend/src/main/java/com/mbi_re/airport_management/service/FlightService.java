package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.config.TenantContext;
import com.mbi_re.airport_management.dto.FlightDTO;
import com.mbi_re.airport_management.model.Flight;
import com.mbi_re.airport_management.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlightService {

    @Autowired
    private FlightRepository flightRepository;

    public FlightDTO createFlight(FlightDTO dto) {
        String tenantId = TenantContext.getTenantId();
        Flight flight = dtoToEntity(dto);
        flight.setTenantId(tenantId);
        return entityToDto(flightRepository.save(flight));
    }

    public List<FlightDTO> getAllFlights() {
        String tenantId = TenantContext.getTenantId();
        return flightRepository.findByTenantId(tenantId)
                .stream().map(this::entityToDto).collect(Collectors.toList());
    }

    public FlightDTO getFlightById(Long id) {
        String tenantId = TenantContext.getTenantId();
        Flight flight = flightRepository.findById(id)
                .filter(f -> tenantId.equals(f.getTenantId()))
                .orElseThrow(() -> new RuntimeException("Flight not found or not allowed"));
        return entityToDto(flight);
    }

    public FlightDTO updateFlight(Long id, FlightDTO dto) {
        String tenantId = TenantContext.getTenantId();
        Flight flight = flightRepository.findById(id)
                .filter(f -> tenantId.equals(f.getTenantId()))
                .orElseThrow(() -> new RuntimeException("Flight not found or not allowed"));

        flight.setFlightNumber(dto.getFlightNumber());
        flight.setDepartureAirport(dto.getDepartureAirport());
        flight.setArrivalAirport(dto.getArrivalAirport());
        flight.setDepartureTime(dto.getDepartureTime());
        flight.setArrivalTime(dto.getArrivalTime());
        flight.setStatus(dto.getStatus());

        return entityToDto(flightRepository.save(flight));
    }

    public void deleteFlight(Long id) {
        String tenantId = TenantContext.getTenantId();
        Flight flight = flightRepository.findById(id)
                .filter(f -> tenantId.equals(f.getTenantId()))
                .orElseThrow(() -> new RuntimeException("Flight not found or not allowed"));
        flightRepository.deleteById(id);
    }

    public String getFlightStatus(Long flightId) {
        String tenantId = TenantContext.getTenantId();
        Flight flight = flightRepository.findById(flightId)
                .filter(f -> tenantId.equals(f.getTenantId()))
                .orElseThrow(() -> new RuntimeException("Flight not found or not allowed"));
        return flight.getStatus();
    }

    public List<FlightDTO> getLiveFlights() {
        String tenantId = TenantContext.getTenantId();
        return flightRepository.findByStatusAndTenantId("ACTIVE", tenantId)
                .stream().map(this::entityToDto).collect(Collectors.toList());
    }

    private Flight dtoToEntity(FlightDTO dto) {
        Flight flight = new Flight();
        flight.setId(dto.getId());
        flight.setFlightNumber(dto.getFlightNumber());
        flight.setDepartureAirport(dto.getDepartureAirport());
        flight.setArrivalAirport(dto.getArrivalAirport());
        flight.setDepartureTime(dto.getDepartureTime());
        flight.setArrivalTime(dto.getArrivalTime());
        flight.setStatus(dto.getStatus());
        return flight;
    }

    private FlightDTO entityToDto(Flight flight) {
        FlightDTO dto = new FlightDTO();
        dto.setId(flight.getId());
        dto.setFlightNumber(flight.getFlightNumber());
        dto.setDepartureAirport(flight.getDepartureAirport());
        dto.setArrivalAirport(flight.getArrivalAirport());
        dto.setDepartureTime(flight.getDepartureTime());
        dto.setArrivalTime(flight.getArrivalTime());
        dto.setStatus(flight.getStatus());
        dto.setTenantId(flight.getTenantId());
        return dto;
    }
}
