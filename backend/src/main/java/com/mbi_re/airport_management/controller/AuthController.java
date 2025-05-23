package com.mbi_re.airport_management.controller;

import com.mbi_re.airport_management.config.TenantContext;
import com.mbi_re.airport_management.model.User;
import com.mbi_re.airport_management.repository.UserRepository;
import com.mbi_re.airport_management.security.CustomUserDetailsService;
import com.mbi_re.airport_management.security.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Controller for user authentication and JWT token generation.
 * Requires tenant identification via X-Tenant-ID header.
 */
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Endpoints for user login and token generation")
public class AuthController {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Authenticates a user by username and password, scoped to a tenant.
     * Requires 'X-Tenant-ID' header for tenant identification.
     *
     * @param request  the login request containing username and password
     * @param tenantId the tenant identifier from the "X-Tenant-ID" request header
     * @return a ResponseEntity containing a JWT token, user role, tenant ID, and user ID if successful;
     *         400 if tenant header is missing; 401 if authentication fails
     */
    @PostMapping("/login")
    @Operation(
            summary = "Login user",
            description = "Authenticate user and return a JWT token. Requires X-Tenant-ID header."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Login successful",
                    content = @Content(schema = @Schema(implementation = LoginResponse.class))
            ),
            @ApiResponse(responseCode = "401", description = "Invalid username, password, or tenant", content = @Content),
            @ApiResponse(responseCode = "400", description = "Missing tenant ID header", content = @Content)
    })
    public ResponseEntity<?> login(
            @Valid
            @RequestBody
            @Parameter(description = "Login credentials", required = true,
                    content = @Content(schema = @Schema(implementation = LoginRequest.class)))
            LoginRequest request,

            @RequestHeader(value = "X-Tenant-ID", required = true)
            @Parameter(description = "Tenant ID to authenticate against", required = true)
            String tenantId) {

        if (tenantId == null || tenantId.isBlank()) {
            return ResponseEntity.badRequest().body("Missing X-Tenant-ID header.");
        }

        TenantContext.setTenantId(tenantId.toLowerCase());

        Optional<User> userOptional = userRepository.findByUsernameAndTenantId(request.getUsername(), tenantId);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(401).body("Invalid username or tenant.");
        }

        User user = userOptional.get();

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.status(401).body("Invalid password.");
        }

        var userDetails = customUserDetailsService.loadUserByUsernameAndTenant(user.getUsername(), tenantId);
        String token = jwtService.generateToken(userDetails.getUsername(), user.getRole().toString(), tenantId);

        return ResponseEntity.ok(new LoginResponse(
                token,
                user.getRole().name(),
                user.getTenantId(),
                user.getId()
        ));
    }

    /**
     * Request body for login endpoint.
     */
    @Schema(description = "Login request payload")
    public static class LoginRequest {

        @Schema(description = "Username of the user", example = "john.doe", required = true)
        private String username;

        @Schema(description = "Password of the user", example = "password123", required = true)
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

    /**
     * Response returned after successful login.
     */
    @Schema(description = "Login response containing JWT token and user details")
    public static class LoginResponse {

        @Schema(description = "JWT authentication token")
        private String token;

        @Schema(description = "Role of the user", example = "ADMIN")
        private String role;

        @Schema(description = "Tenant ID associated with the user", example = "airport1")
        private String tenantId;

        @Schema(description = "Unique identifier of the user", example = "42")
        private Long userId;

        public LoginResponse() {}

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
