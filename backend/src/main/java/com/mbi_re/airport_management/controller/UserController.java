package com.mbi_re.airport_management.controller;

import com.mbi_re.airport_management.config.TenantContext;
import com.mbi_re.airport_management.dto.UserDTO;
import com.mbi_re.airport_management.model.User;
import com.mbi_re.airport_management.security.JwtService;
import com.mbi_re.airport_management.service.UserService;
import com.mbi_re.airport_management.utils.TenantUtil;
import io.swagger.v3.oas.annotations.Operation;
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

@RestController
@RequestMapping("/api/auth")
@Tag(name = "User Authentication and Profile", description = "Endpoints for user registration and profile management.")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    /**
     * Merr të dhënat e përdoruesit të kyçur.
     */
    @GetMapping("/profile")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(summary = "Get user profile")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Profile retrieved",
                    content = @Content(schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @Cacheable(value = "userProfiles", key = "#root.methodName + '_' + T(com.mbi_re.airport_management.config.TenantContext).getTenantId()")
    public ResponseEntity<UserDTO> getProfile() {
        String tenantId = getTenantFromContext();
        TenantUtil.validateTenant(tenantId);
        UserDTO profile = userService.getCurrentUserProfile();
        return ResponseEntity.ok(profile);
    }

    /**
     * Përditëson profilin e përdoruesit të kyçur.
     */
    @PutMapping("/profile")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(summary = "Update user profile")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Profile updated",
                    content = @Content(schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<UserDTO> updateProfile(@RequestBody UserDTO updatedUser) {
        String tenantId = getTenantFromContext();
        TenantUtil.validateTenant(tenantId);
        UserDTO updatedProfile = userService.updateUserProfile(updatedUser);
        return ResponseEntity.ok(updatedProfile);
    }

    private String getTenantFromContext() {
        String tenantId = TenantContext.getTenantId();
        if (tenantId == null || tenantId.isEmpty()) {
            throw new IllegalStateException("Tenant not found in context.");
        }
        return tenantId;
    }

    /**
     * Regjistron një përdorues të ri për tenantin e dhënë.
     */
    @PostMapping("/register")
    @Operation(summary = "Register a new user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User registered successfully",
                    content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<User> register(@RequestBody UserDTO userDTO,
                                         @RequestHeader("X-Tenant-ID") String tenantId) {
        TenantUtil.validateTenant(tenantId);
        User createdUser = userService.registerUser(userDTO, tenantId);
        return ResponseEntity.ok(createdUser);
    }

    /**
     * Merr një përdorues me ID. Vetëm për ADMIN.
     */
    @GetMapping("/user/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get user by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User found",
                    content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @Cacheable(value = "usersById", key = "#id + '_' + T(com.mbi_re.airport_management.config.TenantContext).getTenantId()")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        String tenantId = getTenantFromContext();
        TenantUtil.validateTenant(tenantId);
        Optional<User> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}