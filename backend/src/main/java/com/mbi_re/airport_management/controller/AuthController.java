package com.mbi_re.airport_management.controller;

import com.mbi_re.airport_management.config.TenantContext;
import com.mbi_re.airport_management.model.Role;
import com.mbi_re.airport_management.model.User;
import com.mbi_re.airport_management.repository.UserRepository;
import com.mbi_re.airport_management.security.CustomUserDetailsService;
import com.mbi_re.airport_management.security.JwtService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private CustomUserDetailsService customUserDetailsService;
    @Autowired private JwtService jwtService;
    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @Valid @RequestBody LoginRequest request,
            @RequestHeader("X-Tenant-ID") String tenantId) {

        TenantContext.setTenantId(tenantId); // multi-tenancy context

        Optional<User> userOptional = userRepository.findByUsernameAndTenantId(request.getUsername(), tenantId);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(401).body("Invalid username or tenant.");
        }

        User user = userOptional.get();

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.status(401).body("Invalid password.");
        }

        UserDetails userDetails = customUserDetailsService.loadUserByUsernameAndTenant(user.getUsername(), tenantId);
        String token = jwtService.generateToken(userDetails.getUsername(), user.getRole().toString(), tenantId);

        return ResponseEntity.ok(new LoginResponse(
                token,
                user.getRole().name(),
                user.getTenantId(),
                user.getId()
        ));
    }

    // DTOs
    public static class LoginRequest {
        private String username;
        private String password;

        public LoginRequest() {}

        public LoginRequest(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    public static class LoginResponse {
        private String token;
        private String role;
        private String tenantId;
        private Long userId;

        public LoginResponse(String token, String role, String tenantId, Long userId) {
            this.token = token;
            this.role = role;
            this.tenantId = tenantId;
            this.userId = userId;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getTenantId() {
            return tenantId;
        }

        public void setTenantId(String tenantId) {
            this.tenantId = tenantId;
        }

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }
    }
}
