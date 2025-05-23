package com.mbi_re.airport_management.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mbi_re.airport_management.controller.AuthController.LoginRequest;
import com.mbi_re.airport_management.controller.AuthController.LoginResponse;
import com.mbi_re.airport_management.model.Role;
import com.mbi_re.airport_management.model.User;
import com.mbi_re.airport_management.repository.UserRepository;
import com.mbi_re.airport_management.security.CustomUserDetailsService;
import com.mbi_re.airport_management.security.JwtService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
@WithMockUser
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void login_shouldReturnToken_whenCredentialsAreValid() throws Exception {
        // Given
        String username = "admin";
        String password = "1234";
        String tenantId = "default";
        String token = "mock-jwt-token";

        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setUsername(username);
        mockUser.setPassword("encoded-password");
        mockUser.setRole(Role.ADMIN);
        mockUser.setTenantId(tenantId);

        LoginRequest request = new LoginRequest(username, password);

        when(userRepository.findByUsernameAndTenantId(username, tenantId)).thenReturn(Optional.of(mockUser));
        when(passwordEncoder.matches(password, mockUser.getPassword())).thenReturn(true);
        when(customUserDetailsService.loadUserByUsernameAndTenant(username, tenantId)).thenReturn(org.springframework.security.core.userdetails.User
                .withUsername(username)
                .password("encoded-password")
                .roles("ADMIN")
                .build());
        when(jwtService.generateToken(username, "ADMIN", tenantId)).thenReturn(token);

        // When / Then
        mockMvc.perform(post("/api/auth/login")
                        .header("X-Tenant-ID", tenantId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(token))
                .andExpect(jsonPath("$.role").value("ADMIN"))
                .andExpect(jsonPath("$.tenantId").value(tenantId))
                .andExpect(jsonPath("$.userId").value(1));
    }

    @Test
    void login_shouldReturn401_whenUserNotFound() throws Exception {
        LoginRequest request = new LoginRequest("missing", "password");

        when(userRepository.findByUsernameAndTenantId("missing", "default")).thenReturn(Optional.empty());

        mockMvc.perform(post("/api/auth/login")
                        .header("X-Tenant-ID", "default")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Invalid username or tenant."));
    }

    @Test
    void login_shouldReturn401_whenPasswordIsInvalid() throws Exception {
        String username = "admin";
        String tenantId = "default";

        User mockUser = new User();
        mockUser.setUsername(username);
        mockUser.setPassword("encoded-password");
        mockUser.setTenantId(tenantId);

        when(userRepository.findByUsernameAndTenantId(username, tenantId)).thenReturn(Optional.of(mockUser));
        when(passwordEncoder.matches("wrong-password", "encoded-password")).thenReturn(false);

        LoginRequest request = new LoginRequest(username, "wrong-password");

        mockMvc.perform(post("/api/auth/login")
                        .header("X-Tenant-ID", tenantId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Invalid password."));
    }

    @Test
    void login_shouldReturn400_whenTenantIdMissing() throws Exception {
        LoginRequest request = new LoginRequest("admin", "password");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

}
