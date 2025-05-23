package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.dto.AirlineDTO;
import com.mbi_re.airport_management.model.Airline;
import com.mbi_re.airport_management.repository.AirlineRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AirlineServiceTest {

    private AirlineRepository airlineRepository;
    private AirlineService airlineService;

    @BeforeEach
    void setUp() {
        airlineRepository = mock(AirlineRepository.class);
        airlineService = new AirlineService();

        // Inject the mock manually since AirlineService uses field injection
        var field = AirlineService.class.getDeclaredFields()[0];
        field.setAccessible(true);
        try {
            field.set(airlineService, airlineRepository);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testGetAllAirlines() {
        String tenantId = "tenant_123";
        Airline airline1 = new Airline();
        airline1.setId(1L);
        airline1.setName("Air Alpha");
        airline1.setTenantId(tenantId);

        Airline airline2 = new Airline();
        airline2.setId(2L);
        airline2.setName("Beta Air");
        airline2.setTenantId(tenantId);

        when(airlineRepository.findByTenantId(tenantId))
                .thenReturn(Arrays.asList(airline1, airline2));

        List<AirlineDTO> result = airlineService.getAllAirlines(tenantId);

        assertEquals(2, result.size());
        assertEquals("Air Alpha", result.get(0).getName());
        assertEquals("Beta Air", result.get(1).getName());
    }

    @Test
    void testCreateAirlineSuccess() {
        String tenantId = "tenant_123";
        AirlineDTO dto = new AirlineDTO(null, "New Airline");

        when(airlineRepository.existsByNameAndTenantId(dto.getName(), tenantId))
                .thenReturn(false);

        Airline saved = new Airline();
        saved.setId(10L);
        saved.setName(dto.getName());
        saved.setTenantId(tenantId);

        when(airlineRepository.save(Mockito.any(Airline.class)))
                .thenReturn(saved);

        AirlineDTO result = airlineService.createAirline(dto, tenantId);

        assertNotNull(result);
        assertEquals(10L, result.getId());
        assertEquals("New Airline", result.getName());
    }

    @Test
    void testCreateAirlineAlreadyExists() {
        String tenantId = "tenant_123";
        AirlineDTO dto = new AirlineDTO(null, "DuplicateAir");

        when(airlineRepository.existsByNameAndTenantId(dto.getName(), tenantId))
                .thenReturn(true);

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                airlineService.createAirline(dto, tenantId));

        assertEquals("Airline already exists for this tenant!", exception.getMessage());
    }
}
