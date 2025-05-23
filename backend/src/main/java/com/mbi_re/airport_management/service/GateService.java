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
import org.springframework.transaction.annotation.Transactional;

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
     * <p>
     * This method uses caching to improve performance by storing gate data per tenant.
     *
     * @param tenantId the tenant ID for filtering gates
     * @return list of Gate entities associated with the given tenant
     */
    @Cacheable(value = "gates", key = "#tenantId")
    public List<Gate> getAllGates(String tenantId) {
        return gateRepository.findByTenantId(tenantId);
    }

    /**
     * Converts a Gate entity to its corresponding DTO representation.
     * <p>
     * The DTO includes gate details and associated terminal and flight info.
     *
     * @param gate the Gate entity to convert
     * @return a GateResponseDTO containing gate and related information
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
     * Creates a new gate for the specified tenant.
     * <p>
     * The method validates the terminal association, optionally associates a flight,
     * and evicts the "gates" cache for the tenant to keep data consistent.
     *
     * @param dto      the GateDTO containing data to create the gate
     * @param tenantId the tenant ID for whom the gate is created
     * @return the saved Gate entity
     * @throws IllegalArgumentException if terminal ID in DTO is null
     * @throws EntityNotFoundException  if the terminal does not exist for the tenant
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
            flightRepository.findByIdAndTenantId(dto.getFlightId(), tenantId)
                    .ifPresent(gate::setFlight);
        }

        return gateRepository.save(gate);
    }

    /**
     * Deletes a gate identified by ID for the specified tenant.
     * <p>
     * Only gates owned by the tenant can be deleted. The cache for "gates" is evicted after deletion.
     *
     * @param id       the ID of the gate to delete
     * @param tenantId the tenant ID to scope the deletion
     * @throws EntityNotFoundException if gate does not exist or is not owned by the tenant
     */
    @CacheEvict(value = "gates", key = "#tenantId")
    @Transactional
    public void deleteGate(Long id, String tenantId) {
        Gate gate = gateRepository.findByIdAndTenantId(id, tenantId)
                .orElseThrow(() -> new EntityNotFoundException("Gate not found or not owned by tenant"));

        gateRepository.delete(gate);
    }

    /**
     * Updates an existing gate for the given tenant.
     * <p>
     * The method updates gate number, status, terminal, and flight association.
     * If no flight ID is provided, any existing flight assignment is cleared.
     * Cache for "gates" is evicted to keep the data fresh.
     *
     * @param id       the ID of the gate to update
     * @param dto      the GateDTO containing updated information
     * @param tenantId the tenant ID to scope the update operation
     * @return the updated Gate entity
     * @throws EntityNotFoundException if the gate, terminal, or flight (if provided) is not found for the tenant
     */
    @CacheEvict(value = "gates", key = "#tenantId")
    public Gate updateGate(Long id, GateDTO dto, String tenantId) {
        Gate existingGate = gateRepository.findByIdAndTenantId(id, tenantId)
                .orElseThrow(() -> new EntityNotFoundException("Gate not found or not owned by tenant"));

        if (dto.getGateNumber() != null) {
            existingGate.setGateNumber(dto.getGateNumber());
        }

        if (dto.getStatus() != null) {
            existingGate.setStatus(dto.getStatus());
        }

        if (dto.getTerminalId() != null) {
            Terminal terminal = terminalRepository.findByIdAndTenantId(dto.getTerminalId(), tenantId)
                    .orElseThrow(() -> new EntityNotFoundException("Terminal not found for ID: " + dto.getTerminalId()));
            existingGate.setTerminal(terminal);
        }

        if (dto.getFlightId() != null) {
            Flight flight = flightRepository.findByIdAndTenantId(dto.getFlightId(), tenantId)
                    .orElseThrow(() -> new EntityNotFoundException("Flight not found for ID: " + dto.getFlightId()));
            existingGate.setFlight(flight);
        } else {
            existingGate.setFlight(null); // Clear existing flight if not provided
        }

        return gateRepository.save(existingGate);
    }

}
