package com.mbi_re.airport_management.controller;

import com.mbi_re.airport_management.config.TenantContext;
import com.mbi_re.airport_management.dto.StaffDTO;
import com.mbi_re.airport_management.service.StaffService;
import com.mbi_re.airport_management.utils.TenantUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing staff members.
 * <p>
 * All operations require tenant validation via the "X-Tenant-ID" header
 * and ADMIN role authorization.
 * </p>
 */
@RestController
@RequestMapping("/api/staff")
@Tag(name = "Staff Controller", description = "API endpoints for managing staff members per tenant")
public class StaffController {

    @Autowired
    private StaffService service;

    /**
     * Create a new staff member.
     * <p>
     * Requires ADMIN role.
     * Validates the tenant from the "X-Tenant-ID" header.
     * </p>
     *
     * @param dto      Staff data to create
     * @param tenantId Tenant ID from request header for validation
     * @return The created StaffDTO object
     */
    @Operation(summary = "Create a new staff member", description = "Requires ADMIN privileges. Validates tenant via header.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Staff created successfully"),
            @ApiResponse(responseCode = "403", description = "Invalid tenant ID or unauthorized")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public StaffDTO create(
            @Parameter(description = "Staff data to create", required = true)
            @RequestBody StaffDTO dto,
            @Parameter(description = "Tenant ID from header for validation", required = true)
            @RequestHeader("X-Tenant-ID") String tenantId) {
        TenantUtil.validateTenant(tenantId);
        return service.create(dto);
    }

    /**
     * Retrieve all staff members for the tenant.
     * <p>
     * Requires ADMIN role.
     * Validates tenant from the "X-Tenant-ID" header.
     * </p>
     *
     * @param tenantId Tenant ID from request header for validation
     * @return List of all StaffDTO objects
     */
    @Operation(summary = "Get all staff members", description = "Requires ADMIN privileges. Validates tenant via header.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of staff retrieved successfully"),
            @ApiResponse(responseCode = "403", description = "Invalid tenant ID or unauthorized")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public List<StaffDTO> getAll(
            @Parameter(description = "Tenant ID from header for validation", required = true)
            @RequestHeader("X-Tenant-ID") String tenantId) {
        TenantUtil.validateTenant(tenantId);
        return service.getAll(tenantId);
    }

    /**
     * Retrieve a staff member by their ID.
     * <p>
     * Requires ADMIN role.
     * Validates tenant from the "X-Tenant-ID" header.
     * </p>
     *
     * @param id       ID of the staff member to retrieve
     * @param tenantId Tenant ID from request header for validation
     * @return The StaffDTO corresponding to the given ID
     */
    @Operation(summary = "Get staff member by ID", description = "Requires ADMIN privileges. Validates tenant via header.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Staff member found"),
            @ApiResponse(responseCode = "403", description = "Invalid tenant ID or unauthorized"),
            @ApiResponse(responseCode = "404", description = "Staff member not found")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public StaffDTO getById(
            @Parameter(description = "ID of the staff member", required = true)
            @PathVariable Long id,
            @Parameter(description = "Tenant ID from header for validation", required = true)
            @RequestHeader("X-Tenant-ID") String tenantId) {
        TenantUtil.validateTenant(tenantId);
        return service.getByIdAndTenantId(id);
    }

    /**
     * Update an existing staff member.
     * <p>
     * Requires ADMIN role.
     * Validates tenant from the "X-Tenant-ID" header.
     * </p>
     *
     * @param id       ID of the staff member to update
     * @param dto      Updated staff data
     * @param tenantId Tenant ID from request header for validation
     * @return The updated StaffDTO object
     */
    @Operation(summary = "Update staff member", description = "Requires ADMIN privileges. Validates tenant via header.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Staff member updated successfully"),
            @ApiResponse(responseCode = "403", description = "Invalid tenant ID or unauthorized"),
            @ApiResponse(responseCode = "404", description = "Staff member not found")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public StaffDTO update(
            @Parameter(description = "ID of the staff member to update", required = true)
            @PathVariable Long id,
            @Parameter(description = "Updated staff data", required = true)
            @RequestBody StaffDTO dto,
            @Parameter(description = "Tenant ID from header for validation", required = true)
            @RequestHeader("X-Tenant-ID") String tenantId) {
        TenantUtil.validateTenant(tenantId);
        return service.update(id, dto);
    }

    /**
     * Delete a staff member by their ID.
     * <p>
     * Requires ADMIN role.
     * Validates tenant from the "X-Tenant-ID" header.
     * </p>
     *
     * @param id       ID of the staff member to delete
     * @param tenantId Tenant ID from request header for validation
     */
    @Operation(summary = "Delete staff member", description = "Requires ADMIN privileges. Validates tenant via header.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Staff member deleted successfully"),
            @ApiResponse(responseCode = "403", description = "Invalid tenant ID or unauthorized"),
            @ApiResponse(responseCode = "404", description = "Staff member not found")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void delete(
            @Parameter(description = "ID of the staff member to delete", required = true)
            @PathVariable Long id,
            @Parameter(description = "Tenant ID from header for validation", required = true)
            @RequestHeader("X-Tenant-ID") String tenantId) {
        TenantUtil.validateTenant(tenantId);
        service.delete(id);
    }
}
