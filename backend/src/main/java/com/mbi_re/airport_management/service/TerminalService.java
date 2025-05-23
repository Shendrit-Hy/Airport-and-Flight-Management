package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.dto.TerminalDTO;
import com.mbi_re.airport_management.model.Airport;
import com.mbi_re.airport_management.model.Terminal;
import com.mbi_re.airport_management.repository.AirportRepository;
import com.mbi_re.airport_management.repository.TerminalRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service layer for managing terminals.
 * Provides business logic for retrieving and creating terminals within a tenant context.
 */
@Service
public class TerminalService {

    private final TerminalRepository terminalRepository;
    private final AirportRepository airportRepository;

    public TerminalService(TerminalRepository terminalRepository, AirportRepository airportRepository) {
        this.terminalRepository = terminalRepository;
        this.airportRepository = airportRepository;
    }

    /**
     * Retrieves all terminals for a specific tenant.
     * Results are cached by tenantId to improve performance on repeated queries.
     *
     * @param tenantId the ID of the tenant to filter terminals
     * @return a list of TerminalDTO objects representing terminals belonging to the tenant
     */
    @Cacheable(value = "terminals", key = "#tenantId")
    public List<TerminalDTO> getAllTerminals(String tenantId) {
        return terminalRepository.findByTenantId(tenantId).stream()
                .map(terminal -> {
                    TerminalDTO dto = new TerminalDTO();
                    dto.setId(terminal.getId());
                    dto.setName(terminal.getName());
                    dto.setAirportId(terminal.getAirport().getId());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    /**
     * Creates a new terminal associated with a given airport and tenant.
     * Validates the presence of the airportId and the existence of the referenced airport.
     *
     * @param dto      the TerminalDTO containing terminal data to create
     * @param tenantId the ID of the tenant for which the terminal is created
     * @return the created TerminalDTO reflecting the saved terminal data
     * @throws IllegalArgumentException if airportId in dto is null
     * @throws RuntimeException         if the airport with the provided ID does not exist
     */
    public TerminalDTO createTerminal(TerminalDTO dto, String tenantId) {
        if (dto.getAirportId() == null) {
            throw new IllegalArgumentException("Airport ID must not be null");
        }

        Airport airport = airportRepository.findById(dto.getAirportId())
                .orElseThrow(() -> new RuntimeException("Airport not found"));

        Terminal terminal = new Terminal();
        terminal.setName(dto.getName());
        terminal.setAirport(airport);
        terminal.setTenantId(tenantId);

        Terminal savedTerminal = terminalRepository.save(terminal);

        TerminalDTO responseDto = new TerminalDTO();
        responseDto.setId(savedTerminal.getId());
        responseDto.setName(savedTerminal.getName());
        responseDto.setAirportId(savedTerminal.getAirport().getId());

        return responseDto;
    }

    /**
     * Deletes a terminal by its ID for a specific tenant.
     * Validates the terminal's existence and ownership by the tenant before deletion.
     *
     * @param terminalId the ID of the terminal to delete
     * @param tenantId   the tenant ID used to verify ownership
     * @throws RuntimeException if terminal is not found or does not belong to the specified tenant
     */
    public void deleteTerminal(Long terminalId, String tenantId) {
        Terminal terminal = terminalRepository.findById(terminalId)
                .orElseThrow(() -> new RuntimeException("Terminal not found"));

        if (!terminal.getTenantId().equals(tenantId)) {
            throw new RuntimeException("Terminal does not belong to the tenant");
        }

        terminalRepository.delete(terminal);
    }

}
