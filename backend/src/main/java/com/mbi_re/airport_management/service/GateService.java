package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.dto.GateDTO;
import com.mbi_re.airport_management.dto.GateResponseDTO;
import com.mbi_re.airport_management.model.Flight;
import com.mbi_re.airport_management.model.Gate;
import com.mbi_re.airport_management.model.Terminal;
import com.mbi_re.airport_management.repository.FlightRepository;
import com.mbi_re.airport_management.repository.GateRepository;
import com.mbi_re.airport_management.repository.TerminalRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GateService {

    private final GateRepository gateRepository;
    private final TerminalRepository terminalRepository;
    private final FlightRepository flightRepository;

    public GateService(GateRepository gateRepository,
                       TerminalRepository terminalRepository,
                       FlightRepository flightRepository) {
        this.gateRepository = gateRepository;
        this.terminalRepository = terminalRepository;
        this.flightRepository = flightRepository;
    }

    public List<Gate> getAllGates(String tenantId) {
        return gateRepository.findByTenantId(tenantId);
    }

    public GateResponseDTO mapToResponseDTO(Gate gate) {
        GateResponseDTO dto = new GateResponseDTO();
        dto.setId(gate.getId());
        dto.setGateNumber(gate.getGateNumber());
        dto.setStatus(gate.getStatus());
        dto.setTerminalId(gate.getTerminal().getId());
        dto.setTerminalName(gate.getTerminal().getName());
        if (gate.getFlight() != null) {
            dto.setFlightId(gate.getFlight().getId());
        }
        return dto;
    }


    public Gate createGate(GateDTO dto, String tenantId) {
        if (dto.getTerminalId() == null) {
            throw new IllegalArgumentException("Terminal ID must not be null");
        }

        Terminal terminal = terminalRepository.findByIdAndTenantId(dto.getTerminalId(), tenantId)
                .orElseThrow(() -> new EntityNotFoundException("Terminal not found for ID: " + dto.getTerminalId()));


        Gate gate = new Gate();
        gate.setGateNumber(dto.getGateNumber());
        gate.setStatus(dto.getStatus());
        gate.setTerminal(terminal);
        gate.setTenantId(tenantId);

        // Optional: set flight if provided
        if (dto.getFlightId() != null) {
            Flight flight = null;
            if (dto.getFlightId() != null) {
                flight = flightRepository.findByIdAndTenantId(dto.getFlightId(), tenantId)
                        .orElseThrow(() -> new EntityNotFoundException("Flight not found for ID: " + dto.getFlightId()));
            }
            gate.setFlight(flight);
        }

        return gateRepository.save(gate);
    }
}

