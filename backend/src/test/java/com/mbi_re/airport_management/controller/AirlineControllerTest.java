package com.mbi_re.airport_management.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mbi_re.airport_management.dto.AirlineDTO;
import com.mbi_re.airport_management.service.AirlineService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AirlineControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AirlineService airlineService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser
    void getAirlinesShouldReturnOk() throws Exception {
        // Mock service response
        when(airlineService.getAllAirlines("default"))
                .thenReturn(List.of(new AirlineDTO(1L, "Test Airline")));

        mockMvc.perform(get("/api/airlines")
                        .header("X-Tenant-ID", "default"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Test Airline"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void createAirlineShouldReturnCreatedAirline() throws Exception {
        AirlineDTO dto = new AirlineDTO(null, "New Airline");
        AirlineDTO createdDto = new AirlineDTO(2L, "New Airline");


        when(airlineService.createAirline(any(AirlineDTO.class), any(String.class)))
                .thenReturn(createdDto);

        mockMvc.perform(post("/api/airlines")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                        .header("X-Tenant-ID", "default"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2L))
                .andExpect(jsonPath("$.name").value("New Airline"));
    }
}
