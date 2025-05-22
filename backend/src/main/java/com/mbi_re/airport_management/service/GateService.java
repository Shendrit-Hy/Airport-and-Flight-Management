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
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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

    /**
     * Retrieves all gates for a specific tenant.
     * This method is cached to improve performance when gates don't change frequently.
     *
     * @param tenantId the tenant ID for filtering gates
     * @return list of Gate entities for the given tenant
     */
    @Cacheable(value = "gates", key = "#tenantId")
    public List<Gate> getAllGates(String tenantId) {
        return gateRepository.findByTenantId(tenantId);
    }

    /**
     * Converts a Gate entity into a GateResponseDTO.
     *
     * @param gate the gate entity to convert
     * @return a DTO representation of the gate
     */
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

    /**
     * Creates a new gate for a tenant.
     * This method ensures tenant-specific associations for terminal and optional flight.
     * Cache for "gates" is evicted to keep data fresh.
     *
     * @param dto      the gate DTO containing user-provided data
     * @param tenantId the tenant ID for whom the gate is being created
     * @return the newly created Gate entity
     */
    @CacheEvict(value = "gates", key = "#tenantId")
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

        if (dto.getFlightId() != null) {
            Flight flight = flightRepository.findByIdAndTenantId(dto.getFlightId(), tenantId)
                    .orElseThrow(() -> new EntityNotFoundException("Flight not found for ID: " + dto.getFlightId()));
            gate.setFlight(flight);
        }

        return gateRepository.save(gate);
    }

    /**
     * Deletes a gate for the given tenant.
     * This method ensures that only tenant-owned gates can be deleted.
     * Cache for "gates" is evicted to reflect the change.
     *
     * @param id       the ID of the gate to delete
     * @param tenantId the tenant ID to ensure scoping
     */
    @CacheEvict(value = "gates", key = "#tenantId")
    public void deleteGate(Long id, String tenantId) {
        Gate gate = gateRepository.findByIdAndTenantId(id, tenantId)
                .orElseThrow(() -> new EntityNotFoundException("Gate not found or not owned by tenant"));

        gateRepository.delete(gate);
    }
}
