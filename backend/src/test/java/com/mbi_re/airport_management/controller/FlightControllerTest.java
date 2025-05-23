package com.mbi_re.airport_management.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mbi_re.airport_management.config.TenantContext;
import com.mbi_re.airport_management.dto.FlightDTO;
import com.mbi_re.airport_management.model.FlightStatus;
import com.mbi_re.airport_management.repository.FlightRepository;
import com.mbi_re.airport_management.security.JwtAuthenticationFilter;
import com.mbi_re.airport_management.security.JwtService;
import com.mbi_re.airport_management.service.FlightService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(FlightController.class)
@WithMockUser
@AutoConfigureMockMvc
public class FlightControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FlightService flightService;
    @MockBean
    private JwtService jwtService;
    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockBean
    private FlightRepository flightRepository;

    @Autowired
    private ObjectMapper objectMapper;


    private FlightDTO sampleFlight;

    @BeforeEach
    void setup() {
        sampleFlight = new FlightDTO();
        sampleFlight.setId(1L);
        sampleFlight.setFlightNumber("XY123");
        sampleFlight.setDepartureAirport("JFK");
        sampleFlight.setArrivalAirport("LAX");
        sampleFlight.setDepartureTime(LocalTime.of(10, 0));
        sampleFlight.setArrivalTime(LocalTime.of(13, 0));
        sampleFlight.setFlightDate(LocalDate.now().plusDays(1));
        sampleFlight.setAvailableSeat(100);
        sampleFlight.setPrice(150.0);
        sampleFlight.setFlightStatus(FlightStatus.SCHEDULED);
        sampleFlight.setTenantId("tenant1");
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturnAllFlightsForAdmin() throws Exception {
        Mockito.when(flightService.getAllFlights(anyString())).thenReturn(List.of(sampleFlight));

        mockMvc.perform(get("/api/flights/all"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldAddFlight() throws Exception {
        Mockito.when(flightService.addFlight(any(FlightDTO.class))).thenReturn(sampleFlight);


        mockMvc.perform(post("/api/flights")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleFlight))
                .with(csrf()))
                .andExpect(status().isOk());
    }


    @Test
    void shouldReturnFilteredFlights() throws Exception {
        mockMvc.perform(get("/api/flights/filter")
                        .header("X-Tenant-ID", "default")
                        .param("from", "JFK")
                        .param("to", "LAX")
                        .param("startDate", LocalDate.now().toString())
                        .param("endDate", LocalDate.now().plusDays(5).toString())
                        .param("passengers", "1"))
                .andExpect(status().isOk());
    }
}
