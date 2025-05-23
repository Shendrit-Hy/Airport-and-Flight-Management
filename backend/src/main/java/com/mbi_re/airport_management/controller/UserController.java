package com.mbi_re.airport_management.controller;

import com.mbi_re.airport_management.dto.UserDTO;
import com.mbi_re.airport_management.model.User;
import com.mbi_re.airport_management.security.JwtService;
import com.mbi_re.airport_management.service.UserService;
import com.mbi_re.airport_management.utils.TenantUtil;
import com.mbi_re.airport_management.config.TenantContext;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Controller handling user registration and profile management operations
 * in a multitenant environment.
 *
 * <p>
 * Tenant validation differs based on authentication status:
 * - Pre-authenticated endpoints (like register) expect tenant from header or host.
 * - Authenticated endpoints extract tenant from JWT via {@link TenantContext}.
 * </p>
 */
@RestController
@RequestMapping("/api/auth")
@Tag(name = "User Authentication and Profile", description = "Endpoints for user registration and profile management.")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    /**
     * Registers a new user under the current tenant.
     *
     * @param userDTO  The user registration information.
     * @param tenantId The tenant ID from the request header.
     * @return The created {@link User} entity.
     */
    @PostMapping("/register")
    @Operation(
            summary = "Register a new user",
            description = "Creates a new user within the specified tenant.",
            parameters = {
                    @Parameter(name = "X-Tenant-ID", description = "Tenant ID for user registration", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "User registered successfully",
                            content = @Content(schema = @Schema(implementation = User.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input or tenant", content = @Content)
            }
    )
    public ResponseEntity<User> register(
            @RequestBody UserDTO userDTO,
            @RequestHeader(value = "X-Tenant-ID") String tenantId) {

        TenantUtil.validateTenant(tenantId);
        User createdUser = userService.registerUser(userDTO, tenantId);
        return ResponseEntity.ok(createdUser);
    }

    /**
     * Retrieves the currently authenticated user's profile.
     * Uses the tenant extracted from the security context.
     *
     * @return The current user's {@link UserDTO} profile.
     */
    @GetMapping("/profile")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(
            summary = "Get user profile",
            description = "Returns the profile of the currently authenticated user.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Profile retrieved successfully",
                            content = @Content(schema = @Schema(implementation = UserDTO.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
            }
    )

    public ResponseEntity<UserDTO> getProfile() {
        String tenantId = getTenantFromContext();
        TenantUtil.validateTenant(tenantId);
        UserDTO profile = userService.getCurrentUserProfile();
        return ResponseEntity.ok(profile);
    }

    /**
     * Retrieves user information by user ID.
     * Accessible only to users with ADMIN role.
     *
     * @param id The ID of the user to retrieve.
     * @return The {@link User} object if found, or 404 otherwise.
     */
    @GetMapping("/user/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Get user by ID",
            description = "Fetches a user's details by their ID. Only accessible by admins.",
            parameters = {
                    @Parameter(name = "id", description = "ID of the user to retrieve", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "User found",
                            content = @Content(schema = @Schema(implementation = User.class))),
                    @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
            }
    )
    @Cacheable(value = "usersById", key = "#id + '_' + T(com.mbi_re.airport_management.config.TenantContext).getTenantId()")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        String tenantId = getTenantFromContext();
        TenantUtil.validateTenant(tenantId);
        Optional<User> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Updates the currently authenticated user's profile.
     *
     * @param updatedUser A {@link UserDTO} object containing updated user information.
     * @return The updated {@link UserDTO} profile.
     */
    @PutMapping("/profile")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(
            summary = "Update user profile",
            description = "Updates the profile of the currently authenticated user.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Profile updated successfully",
                            content = @Content(schema = @Schema(implementation = UserDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
            }
    )
    public ResponseEntity<UserDTO> updateProfile(@RequestBody UserDTO updatedUser) {
        String tenantId = getTenantFromContext();
        TenantUtil.validateTenant(tenantId);
        UserDTO updatedProfile = userService.updateUserProfile(updatedUser);
        return ResponseEntity.ok(updatedProfile);
    }

    /**
     * Helper method to extract the tenant ID from the {@link TenantContext}.
     *
     * @return The current tenant ID from context.
     * @throws IllegalStateException if tenant ID is missing or invalid.
     */
    private String getTenantFromContext() {
        String tenantId = TenantContext.getTenantId();
        if (tenantId == null || tenantId.isEmpty()) {
            throw new IllegalStateException("Tenant not found in context. User might not be authenticated or tenant configuration is missing.");
        }
        return tenantId;
    }
}
