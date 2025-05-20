package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.dto.TerminalDTO;
import com.mbi_re.airport_management.model.Airport;
import com.mbi_re.airport_management.model.Terminal;
import com.mbi_re.airport_management.repository.AirportRepository;
import com.mbi_re.airport_management.repository.TerminalRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TerminalService {

    private final TerminalRepository terminalRepository;
    private final AirportRepository airportRepository;

    public TerminalService(TerminalRepository terminalRepository, AirportRepository airportRepository) {
        this.terminalRepository = terminalRepository;
        this.airportRepository = airportRepository;
    }

    public List<TerminalDTO> getAllTerminals(String tenantId) {
        return terminalRepository.findByTenantId(tenantId).stream()
                .map(t -> {
                    TerminalDTO dto = new TerminalDTO();
                    dto.setId(t.getId());
                    dto.setName(t.getName());
                    dto.setAirportId(t.getAirport().getId());
                    return dto;
                })
                .collect(Collectors.toList());
    }


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

        Terminal saved = terminalRepository.save(terminal);

        // Map saved terminal back to DTO
        TerminalDTO responseDto = new TerminalDTO();
        responseDto.setId(saved.getId());
        responseDto.setName(saved.getName());
        responseDto.setAirportId(saved.getAirport().getId());

        return responseDto;
    }

}
