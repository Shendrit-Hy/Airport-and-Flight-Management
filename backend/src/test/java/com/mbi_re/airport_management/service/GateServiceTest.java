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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GateServiceTest {

    private GateRepository gateRepository;
    private TerminalRepository terminalRepository;
    private FlightRepository flightRepository;
    private GateService gateService;

    @BeforeEach
    void setUp() {
        gateRepository = mock(GateRepository.class);
        terminalRepository = mock(TerminalRepository.class);
        flightRepository = mock(FlightRepository.class);
        gateService = new GateService(gateRepository, terminalRepository, flightRepository);
    }

    @Test
    void getAllGates_ReturnsListOfGates() {
        String tenantId = "tenant1";
        Gate gate = new Gate();
        when(gateRepository.findByTenantId(tenantId)).thenReturn(List.of(gate));

        List<Gate> result = gateService.getAllGates(tenantId);
        assertEquals(1, result.size());
        verify(gateRepository).findByTenantId(tenantId);
    }

    @Test
    void createGate_WithValidData_ReturnsSavedGate() {
        String tenantId = "tenant1";
        GateDTO dto = new GateDTO();
        dto.setGateNumber("A1");
        dto.setStatus("OPEN");
        dto.setTerminalId(1L);
        dto.setFlightId(2L);

        Terminal terminal = new Terminal();
        terminal.setId(1L);
        terminal.setName("Main Terminal");

        Flight flight = new Flight();
        flight.setId(2L);

        when(terminalRepository.findByIdAndTenantId(1L, tenantId)).thenReturn(Optional.of(terminal));
        when(flightRepository.findByIdAndTenantId(2L, tenantId)).thenReturn(Optional.of(flight));
        when(gateRepository.save(any(Gate.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Gate savedGate = gateService.createGate(dto, tenantId);

        assertEquals("A1", savedGate.getGateNumber());
        assertEquals("OPEN", savedGate.getStatus());
        assertEquals(terminal, savedGate.getTerminal());
        assertEquals(flight, savedGate.getFlight());
    }

    @Test
    void createGate_WithMissingTerminal_ThrowsException() {
        String tenantId = "tenant1";
        GateDTO dto = new GateDTO();  // terminalId is null

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> gateService.createGate(dto, tenantId));

        assertEquals("Terminal ID must not be null", ex.getMessage());
    }

    @Test
    void deleteGate_WithValidId_DeletesGate() {
        Long gateId = 1L;
        String tenantId = "tenant1";

        Gate gate = new Gate();
        gate.setId(gateId);
        when(gateRepository.findByIdAndTenantId(gateId, tenantId)).thenReturn(Optional.of(gate));

        gateService.deleteGate(gateId, tenantId);

        verify(gateRepository).delete(gate);
    }

    @Test
    void deleteGate_WithInvalidId_ThrowsException() {
        Long gateId = 1L;
        String tenantId = "tenant1";

        when(gateRepository.findByIdAndTenantId(gateId, tenantId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> gateService.deleteGate(gateId, tenantId));
    }

    @Test
    void mapToResponseDTO_ValidGate_ReturnsDTO() {
        Terminal terminal = new Terminal();
        terminal.setId(10L);
        terminal.setName("T1");

        Flight flight = new Flight();
        flight.setId(99L);

        Gate gate = new Gate();
        gate.setId(1L);
        gate.setGateNumber("B2");
        gate.setStatus("OPEN");
        gate.setTerminal(terminal);
        gate.setFlight(flight);

        GateResponseDTO dto = gateService.mapToResponseDTO(gate);

        assertEquals(1L, dto.getId());
        assertEquals("B2", dto.getGateNumber());
        assertEquals("OPEN", dto.getStatus());
        assertEquals(10L, dto.getTerminalId());
        assertEquals("T1", dto.getTerminalName());
        assertEquals(99L, dto.getFlightId());
    }
}
