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
     * Authenticates a user and returns a JWT token if credentials are valid.
     * This endpoint requires `X-Tenant-ID` header to identify the tenant.
     *
     * @param request the login request containing username and password
     * @param tenantId tenant ID passed via `X-Tenant-ID` header
     * @return JWT token, user role, tenant ID and user ID
     */
    @PostMapping("/login")
    @Operation(summary = "Login user", description = "Authenticate user and return JWT token. Requires X-Tenant-ID header.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful", content = @Content(schema = @Schema(implementation = LoginResponse.class))),
            @ApiResponse(responseCode = "401", description = "Invalid username, password, or tenant"),
            @ApiResponse(responseCode = "400", description = "Missing tenant ID header")
    })
    public ResponseEntity<?> login(
            @Valid @RequestBody
            @Parameter(description = "Login credentials") LoginRequest request,
            @RequestHeader(value = "X-Tenant-ID", required = true)
            @Parameter(description = "Tenant ID to authenticate against") String tenantId) {

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
     * Request body for login.
     */
    @Schema(description = "Login request payload")
    public static class LoginRequest {

        @Schema(description = "Username", example = "john.doe")
        private String username;

        @Schema(description = "Password", example = "password123")
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
    @Schema(description = "Login response containing JWT and user details")
    public static class LoginResponse {

        @Schema(description = "JWT Token")
        private String token;

        @Schema(description = "User Role", example = "ADMIN")
        private String role;

        @Schema(description = "Tenant ID", example = "airport1")
        private String tenantId;

        @Schema(description = "User ID", example = "42")
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
