package com.mbi_re.airport_management.controller;

import com.mbi_re.airport_management.dto.MaintenanceDTO;
import com.mbi_re.airport_management.service.MaintenanceService;
import com.mbi_re.airport_management.utils.TenantUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/maintenance")
@Tag(name = "Maintenance", description = "Endpoints for managing maintenance records")
public class MaintenanceController {

    @Autowired
    private MaintenanceService maintenanceService;

    /**
     * Create a maintenance record.
     *
     * Uses X-Tenant-ID header for unauthenticated users or JWT tenant validation for authenticated users.
     *
     * @param maintenanceDTO maintenance request data
     * @param jwtTenantId    optional tenant ID from JWT (injected via request attribute)
     * @param tenantId       optional tenant ID from header (for unauthenticated)
     * @return created maintenance ID and success message
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create maintenance record",
            description = "Create a new maintenance record. Uses tenant validation from JWT or X-Tenant-ID header.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Maintenance record created"),
            @ApiResponse(responseCode = "403", description = "Invalid or missing tenant ID")
    })
    public ResponseEntity<?> createMaintenance(
            @RequestBody MaintenanceDTO maintenanceDTO,
            @RequestAttribute(name = "jwtTenantId", required = false)
            @Parameter(description = "Tenant ID from JWT") String jwtTenantId,
            @RequestHeader(name = "X-Tenant-ID", required = false)
            @Parameter(description = "Tenant ID from header") String tenantId) {

        if (jwtTenantId != null) {
            TenantUtil.validateTenant(jwtTenantId);
            maintenanceDTO.setTenantId(jwtTenantId);
        } else {
            TenantUtil.validateTenantFromContext();
            maintenanceDTO.setTenantId(tenantId);
        }

        MaintenanceDTO savedDTO = maintenanceService.create(maintenanceDTO);
        return ResponseEntity.status(201)
                .body(Map.of("id", savedDTO.getId(), "message", "Maintenance record created successfully."));
    }

    /**
     * Retrieve maintenance records by optional filters.
     *
     * @param airportCode optional airport code filter
     * @param status      optional maintenance status filter
     * @return list of matching maintenance records
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get maintenance records",
            description = "Retrieve maintenance records filtered by airport code and/or status")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Maintenance records retrieved")
    })
    public ResponseEntity<List<MaintenanceDTO>> getMaintenance(
            @RequestParam(required = false) @Parameter(description = "Airport code") String airportCode,
            @RequestParam(required = false) @Parameter(description = "Maintenance status") String status) {

        List<MaintenanceDTO> result;
        if (airportCode != null && status != null) {
            result = maintenanceService.getByAirportCodeAndStatus(airportCode, status);
        } else if (airportCode != null) {
            result = maintenanceService.getByAirportCode(airportCode);
        } else if (status != null) {
            result = maintenanceService.getByStatus(status);
        } else {
            result = maintenanceService.getAll();
        }
        return ResponseEntity.ok(result);
    }

    /**
     * Delete a maintenance record.
     *
     * Uses X-Tenant-ID header for unauthenticated users or JWT tenant validation for authenticated users.
     *
     * @param id           maintenance record ID
     * @param jwtTenantId  optional tenant ID from JWT
     * @param tenantId     optional tenant ID from header
     * @return no content response
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete maintenance record",
            description = "Delete a maintenance record. Uses tenant validation from JWT or X-Tenant-ID header.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Maintenance record deleted"),
            @ApiResponse(responseCode = "403", description = "Invalid or missing tenant ID")
    })
    public ResponseEntity<?> delete(
            @PathVariable Long id,
            @RequestAttribute(name = "jwtTenantId", required = false)
            @Parameter(description = "Tenant ID from JWT") String jwtTenantId,
            @RequestHeader(name = "X-Tenant-ID", required = false)
            @Parameter(description = "Tenant ID from header") String tenantId) {

        if (jwtTenantId != null) {
            TenantUtil.validateTenant(jwtTenantId);
            maintenanceService.delete(id, jwtTenantId);
        } else {
            TenantUtil.validateTenantFromContext();
            maintenanceService.delete(id, tenantId);
        }

        return ResponseEntity.noContent().build();
    }
}
