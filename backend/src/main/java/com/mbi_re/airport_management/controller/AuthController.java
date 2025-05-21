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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Endpoints for user login and token generation")
public class AuthController {

    @Autowired private CustomUserDetailsService customUserDetailsService;
    @Autowired private JwtService jwtService;
    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;

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

        // Set tenant context
        TenantContext.setTenantId(tenantId.toLowerCase());

        // Check user existence in current tenant
        Optional<User> userOptional = userRepository.findByUsernameAndTenantId(request.getUsername(), tenantId);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(401).body("Invalid username or tenant.");
        }

        User user = userOptional.get();

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.status(401).body("Invalid password.");
        }

        // Load user details and generate JWT
        var userDetails = customUserDetailsService.loadUserByUsernameAndTenant(user.getUsername(), tenantId);
        String token = jwtService.generateToken(userDetails.getUsername(), user.getRole().toString(), tenantId);

        return ResponseEntity.ok(new LoginResponse(
                token,
                user.getRole().name(),
                user.getTenantId(),
                user.getId()
        ));
    }

    // DTOs for request and response

    /**
     * Request body for login.
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "Login request payload")
    public static class LoginRequest {
        @Schema(description = "Username", example = "john.doe")
        private String username;

        @Schema(description = "Password", example = "password123")
        private String password;
    }

    /**
     * Response returned after successful login.
     */
    @Data
    @AllArgsConstructor
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
    }
}
