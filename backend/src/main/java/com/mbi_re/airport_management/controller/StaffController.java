package com.mbi_re.airport_management.controller;

import com.mbi_re.airport_management.config.TenantContext;
import com.mbi_re.airport_management.dto.StaffDTO;
import com.mbi_re.airport_management.service.StaffService;
import com.mbi_re.airport_management.utils.TenantUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/staff")
public class StaffController {

    @Autowired
    private StaffService service;

    /**
     * Create a new staff member. Requires ADMIN role.
     *
     * @param dto      Staff data
     * @param tenantId Tenant ID from the header for validation (used pre-login)
     * @return Created StaffDTO
     */
    @Operation(summary = "Create a new staff member", description = "Requires ADMIN privileges. Uses header tenant validation.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Staff created successfully"),
            @ApiResponse(responseCode = "403", description = "Invalid tenant ID or unauthorized")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public StaffDTO create(
            @RequestBody StaffDTO dto,
            @Parameter(description = "Tenant ID from header") @RequestHeader("X-Tenant-ID") String tenantId) {
        TenantUtil.validateTenant(tenantId);
        return service.create(dto);
    }

    /**
     * Retrieve all staff members. Requires ADMIN role.
     *
     * @param tenantId Tenant ID from the header
     * @return List of staff
     */
    @Operation(summary = "Get all staff", description = "Requires ADMIN privileges. Uses header tenant validation.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List retrieved successfully"),
            @ApiResponse(responseCode = "403", description = "Invalid tenant ID or unauthorized")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public List<StaffDTO> getAll(
            @Parameter(description = "Tenant ID from header") @RequestHeader("X-Tenant-ID") String tenantId) {
        TenantUtil.validateTenant(tenantId);
        return service.getAll(tenantId);
    }

    /**
     * Get a staff member by ID. Requires ADMIN role.
     *
     * @param id       Staff ID
     * @param tenantId Tenant ID from header
     * @return StaffDTO
     */
    @Operation(summary = "Get staff by ID", description = "Requires ADMIN privileges. Uses header tenant validation.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Staff found"),
            @ApiResponse(responseCode = "403", description = "Invalid tenant ID or unauthorized"),
            @ApiResponse(responseCode = "404", description = "Staff not found")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public StaffDTO getById(
            @PathVariable Long id,
            @Parameter(description = "Tenant ID from header") @RequestHeader("X-Tenant-ID") String tenantId) {
        TenantUtil.validateTenant(tenantId);
        return service.getByIdAndTenantId(id);
    }

    /**
     * Update a staff member. Requires ADMIN role.
     *
     * @param id       Staff ID
     * @param dto      Updated staff data
     * @param tenantId Tenant ID from header
     * @return Updated StaffDTO
     */
    @Operation(summary = "Update staff", description = "Requires ADMIN privileges. Uses header tenant validation.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Staff updated"),
            @ApiResponse(responseCode = "403", description = "Invalid tenant ID or unauthorized"),
            @ApiResponse(responseCode = "404", description = "Staff not found")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public StaffDTO update(
            @PathVariable Long id,
            @RequestBody StaffDTO dto,
            @Parameter(description = "Tenant ID from header") @RequestHeader("X-Tenant-ID") String tenantId) {
        TenantUtil.validateTenant(tenantId);
        return service.update(id, dto);
    }

    /**
     * Delete a staff member by ID. Requires ADMIN role.
     *
     * @param id       Staff ID
     * @param tenantId Tenant ID from header
     */
    @Operation(summary = "Delete staff", description = "Requires ADMIN privileges. Uses header tenant validation.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Staff deleted"),
            @ApiResponse(responseCode = "403", description = "Invalid tenant ID or unauthorized"),
            @ApiResponse(responseCode = "404", description = "Staff not found")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void delete(
            @PathVariable Long id,
            @Parameter(description = "Tenant ID from header") @RequestHeader("X-Tenant-ID") String tenantId) {
        TenantUtil.validateTenant(tenantId);
        service.delete(id);
    }
}
