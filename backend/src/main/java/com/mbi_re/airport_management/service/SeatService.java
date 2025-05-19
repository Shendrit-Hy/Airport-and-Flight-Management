package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.dto.SeatDTO;
import com.mbi_re.airport_management.model.Seat;
import com.mbi_re.airport_management.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SeatService {

    @Autowired
    private SeatRepository seatRepository;

    public List<SeatDTO> getAvailableSeats(Long flightId, String tenantId) {
        List<Seat> seats = seatRepository.findByFlightIdAndTenantIdAndBookedFalse(flightId, tenantId);
        return seats.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<SeatDTO> getAllSeats(Long flightId, String tenantId) {
        List<Seat> seats = seatRepository.findByFlightIdAndTenantId(flightId, tenantId);
        return seats.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

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