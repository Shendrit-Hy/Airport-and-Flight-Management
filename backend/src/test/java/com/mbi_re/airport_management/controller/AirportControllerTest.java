package com.mbi_re.airport_management.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mbi_re.airport_management.dto.AirportDTO;
import com.mbi_re.airport_management.model.Airport;
import com.mbi_re.airport_management.service.AirportService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
public class AirportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AirportService airportService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(roles = "ADMIN")
    void getAllAirports_shouldReturnList() throws Exception {
        Airport airport = new Airport();
        airport.setId(2L);
        airport.setName("JFK Airport");
        airport.setCode("JFK");
        when(airportService.getAllAirports("default")).thenReturn(List.of(airport));

        mockMvc.perform(get("/api/airports")
                        .header("X-Tenant-ID", "default"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(2L))
                .andExpect(jsonPath("$[0].name").value("JFK Airport"))
                .andExpect(jsonPath("$[0].code").value("JFK"));
    }

    @Test
    void createAirport_shouldReturnCreatedAirport() throws Exception {
        AirportDTO dto = new AirportDTO();
        dto.setName("LAX Airport");
        dto.setCode("LAX");
        Airport created = new Airport();
        created.setId(2L);
        created.setName("LAX Airport");
        created.setCode("LAX");



        when(airportService.createAirport(any(AirportDTO.class), eq("default"))).thenReturn(created);

        mockMvc.perform(post("/api/airports")
                        .header("X-Tenant-ID", "default")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2L))
                .andExpect(jsonPath("$.name").value("LAX Airport"))
                .andExpect(jsonPath("$.code").value("LAX"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteAirport_shouldReturnNoContent() throws Exception {
        doNothing().when(airportService).deleteAirport(1L, "default");

        mockMvc.perform(delete("/api/airports/1")
                        .header("X-Tenant-ID", "default"))
                .andExpect(status().isNoContent());
    }
}
