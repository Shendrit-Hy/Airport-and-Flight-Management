package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.dto.FlightScheduleDTO;
import com.mbi_re.airport_management.model.FlightSchedule;
import com.mbi_re.airport_management.repository.FlightScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlightScheduleService {

    @Autowired
    private FlightScheduleRepository flightScheduleRepository;

    public FlightScheduleDTO createFlight(FlightScheduleDTO dto) {
        FlightSchedule flightSchedule = dtoToEntity(dto);
        return entityToDto(flightScheduleRepository.save(flightSchedule));
    }

    public List<FlightScheduleDTO> getAllFlights() {
        return flightScheduleRepository.findAll().stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    public FlightScheduleDTO getFlightById(Long id) {
        FlightSchedule flightSchedule = flightScheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Flight not found"));
        return entityToDto(flightSchedule);
    }

    public FlightScheduleDTO updateFlight(Long id, FlightScheduleDTO dto) {
        FlightSchedule flightSchedule = flightScheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Flight not found"));

        flightSchedule.setFlightNumber(dto.getFlightNumber());
        flightSchedule.setDepartureAirport(dto.getDepartureAirport());
        flightSchedule.setArrivalAirport(dto.getArrivalAirport());
        flightSchedule.setDepartureTime(dto.getDepartureTime());
        flightSchedule.setArrivalTime(dto.getArrivalTime());
        flightSchedule.setStatus(dto.getStatus());

        return entityToDto(flightScheduleRepository.save(flightSchedule));
    }

    public void deleteFlight(Long id) {
        flightScheduleRepository.deleteById(id);
    }

    // Helper methods
    private FlightSchedule dtoToEntity(FlightScheduleDTO dto) {
        return new FlightSchedule(
                dto.getId(),
                dto.getFlightNumber(),
                dto.getDepartureAirport(),
                dto.getArrivalAirport(),
                dto.getDepartureTime(),
                dto.getArrivalTime(),
                dto.getStatus()
        );
    }

    private FlightScheduleDTO entityToDto(FlightSchedule flightSchedule) {
        return new FlightScheduleDTO(
                flightSchedule.getId(),
                flightSchedule.getFlightNumber(),
                flightSchedule.getDepartureAirport(),
                flightSchedule.getArrivalAirport(),
                flightSchedule.getDepartureTime(),
                flightSchedule.getArrivalTime(),
                flightSchedule.getStatus()
        );
    }

}
