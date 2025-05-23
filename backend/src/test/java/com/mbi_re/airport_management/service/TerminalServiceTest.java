package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.dto.TerminalDTO;
import com.mbi_re.airport_management.model.Airport;
import com.mbi_re.airport_management.model.Terminal;
import com.mbi_re.airport_management.repository.AirportRepository;
import com.mbi_re.airport_management.repository.TerminalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TerminalServiceTest {

    private TerminalRepository terminalRepository;
    private AirportRepository airportRepository;
    private TerminalService terminalService;

    @BeforeEach
    void setUp() {
        terminalRepository = mock(TerminalRepository.class);
        airportRepository = mock(AirportRepository.class);
        terminalService = new TerminalService(terminalRepository, airportRepository);
    }

    @Test
    void getAllTerminals_returnsMappedDTOs() {
        Terminal terminal = new Terminal();
        terminal.setId(1L);
        terminal.setName("Terminal A");

        Airport airport = new Airport();
        airport.setId(100L);
        terminal.setAirport(airport);
        terminal.setTenantId("tenant1");

        when(terminalRepository.findByTenantId("tenant1"))
                .thenReturn(List.of(terminal));

        List<TerminalDTO> result = terminalService.getAllTerminals("tenant1");

        assertEquals(1, result.size());
        TerminalDTO dto = result.get(0);
        assertEquals(1L, dto.getId());
        assertEquals("Terminal A", dto.getName());
        assertEquals(100L, dto.getAirportId());
    }

    @Test
    void createTerminal_successfullyCreatesTerminal() {
        TerminalDTO inputDto = new TerminalDTO();
        inputDto.setName("New Terminal");
        inputDto.setAirportId(100L);

        Airport airport = new Airport();
        airport.setId(100L);

        Terminal savedTerminal = new Terminal();
        savedTerminal.setId(10L);
        savedTerminal.setName("New Terminal");
        savedTerminal.setAirport(airport);

        when(airportRepository.findById(100L)).thenReturn(Optional.of(airport));
        when(terminalRepository.save(any(Terminal.class))).thenReturn(savedTerminal);

        TerminalDTO result = terminalService.createTerminal(inputDto, "tenant1");

        assertNotNull(result);
        assertEquals(10L, result.getId());
        assertEquals("New Terminal", result.getName());
        assertEquals(100L, result.getAirportId());

        ArgumentCaptor<Terminal> terminalCaptor = ArgumentCaptor.forClass(Terminal.class);
        verify(terminalRepository).save(terminalCaptor.capture());
        assertEquals("tenant1", terminalCaptor.getValue().getTenantId());
    }

    @Test
    void createTerminal_throwsExceptionWhenAirportIdIsNull() {
        TerminalDTO inputDto = new TerminalDTO();
        inputDto.setName("Terminal without Airport");

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                terminalService.createTerminal(inputDto, "tenant1"));

        assertEquals("Airport ID must not be null", ex.getMessage());
    }

    @Test
    void createTerminal_throwsExceptionWhenAirportNotFound() {
        TerminalDTO inputDto = new TerminalDTO();
        inputDto.setName("Terminal");
        inputDto.setAirportId(999L);

        when(airportRepository.findById(999L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                terminalService.createTerminal(inputDto, "tenant1"));

        assertEquals("Airport not found", ex.getMessage());
    }
}
