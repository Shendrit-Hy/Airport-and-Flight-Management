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

/**
 * REST controller for managing maintenance records.
 *
 * <p>Supports creating, retrieving, and deleting maintenance records with tenant validation.</p>
 */
@RestController
@RequestMapping("/api/maintenance")
@Tag(name = "Maintenance", description = "Endpoints for managing maintenance records")
public class MaintenanceController {

    @Autowired
    private MaintenanceService maintenanceService;

    /**
     * Create a new maintenance record.
     *
     * <p>Tenant validation is performed either from JWT tenant ID (authenticated users)
     * or from "X-Tenant-ID" header (unauthenticated users).</p>
     *
     * @param maintenanceDTO the maintenance data transfer object containing maintenance info
     * @param jwtTenantId optional tenant ID extracted from JWT token (via request attribute)
     * @param tenantId optional tenant ID from HTTP header for unauthenticated requests
     * @return ResponseEntity containing created maintenance ID and success message
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Create maintenance record",
            description = "Create a new maintenance record. Uses tenant validation from JWT or X-Tenant-ID header."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Maintenance record created successfully"),
            @ApiResponse(responseCode = "403", description = "Invalid or missing tenant ID")
    })
    public ResponseEntity<?> createMaintenance(
            @RequestBody
            @Parameter(description = "Maintenance data to create", required = true)
            MaintenanceDTO maintenanceDTO,
            @RequestAttribute(name = "jwtTenantId", required = false)
            @Parameter(description = "Tenant ID extracted from JWT token")
            String jwtTenantId,
            @RequestHeader(name = "X-Tenant-ID", required = false)
            @Parameter(description = "Tenant ID from HTTP header")
            String tenantId) {

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
     * Retrieve maintenance records filtered optionally by airport code and/or status.
     *
     * @param airportCode optional filter for airport code
     * @param status optional filter for maintenance status
     * @return ResponseEntity with list of MaintenanceDTO matching the filters or all records if no filters applied
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Get maintenance records",
            description = "Retrieve maintenance records filtered by airport code and/or status"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Maintenance records retrieved successfully")
    })
    public ResponseEntity<List<MaintenanceDTO>> getMaintenance(
            @RequestParam(required = false)
            @Parameter(description = "Airport code to filter maintenance records")
            String airportCode,
            @RequestParam(required = false)
            @Parameter(description = "Maintenance status to filter maintenance records")
            String status) {

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
     * Delete a maintenance record by its ID.
     *
     * <p>Tenant validation is performed either from JWT tenant ID or from "X-Tenant-ID" header.</p>
     *
     * @param id ID of the maintenance record to delete
     * @param jwtTenantId optional tenant ID from JWT token (via request attribute)
     * @param tenantId optional tenant ID from HTTP header for unauthenticated requests
     * @return ResponseEntity with no content status if deletion is successful
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Delete maintenance record",
            description = "Delete a maintenance record by ID. Uses tenant validation from JWT or X-Tenant-ID header."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Maintenance record deleted successfully"),
            @ApiResponse(responseCode = "403", description = "Invalid or missing tenant ID")
    })
    public ResponseEntity<?> delete(
            @PathVariable
            @Parameter(description = "ID of the maintenance record to delete", required = true)
            Long id,
            @RequestAttribute(name = "jwtTenantId", required = false)
            @Parameter(description = "Tenant ID extracted from JWT token")
            String jwtTenantId,
            @RequestHeader(name = "X-Tenant-ID", required = false)
            @Parameter(description = "Tenant ID from HTTP header")
            String tenantId) {

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
