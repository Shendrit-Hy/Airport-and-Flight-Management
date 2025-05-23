package com.mbi_re.airport_management.integration;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.mbi_re.airport_management.dto.UserDTO;
import com.mbi_re.airport_management.model.Country;
import com.mbi_re.airport_management.model.Role;
import com.mbi_re.airport_management.model.User;
import com.mbi_re.airport_management.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

/**
 * Integration test for UserController.
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test") // Optional, use if you want to load test-specific config
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private AirportRepository airportRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private TerminalRepository  terminalRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String TENANT_ID = "default";

    @BeforeEach
    void setup() {

        cityRepository.deleteAll();
        terminalRepository.deleteAll();
        userRepository.deleteAll();
        airportRepository.deleteAll();
        countryRepository.deleteAll();


        Country country = new Country();
        country.setName("TestCountry");
        country.setCode("ASD");
        country.setTenantId(TENANT_ID);
        countryRepository.save(country);
    }

    @Test
    void registerUser_shouldReturn200AndUser() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("testuser");
        userDTO.setFullname("Test User");
        userDTO.setEmail("testuser@example.com");
        userDTO.setPassword("password123");
        userDTO.setCountry("TestCountry");

        mockMvc.perform(post("/api/auth/register")
                        .header("X-Tenant-ID", TENANT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("testuser"))); // basic check for username in response
    }
}
