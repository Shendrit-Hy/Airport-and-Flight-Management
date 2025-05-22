package com.mbi_re.airport_management.controller;

import com.mbi_re.airport_management.config.TenantContext;
import com.mbi_re.airport_management.dto.GateDTO;
import com.mbi_re.airport_management.dto.GateResponseDTO;
import com.mbi_re.airport_management.model.Gate;
import com.mbi_re.airport_management.service.GateService;
import com.mbi_re.airport_management.utils.TenantUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gates")
@Tag(name = "Gates", description = "Endpoints for managing airport gates")
public class GateController {

    private final GateService gateService;

    public GateController(GateService gateService) {
        this.gateService = gateService;
    }

    /**
     * Retrieve all gates for the current tenant.
     *
     * Publicly accessible for unauthenticated users, but requires tenant header.
     * Validates tenant context against X-Tenant-ID.
     *
     * @param request HTTP request containing headers
     * @return List of GateResponseDTO objects
     */
    @GetMapping
    @Operation(summary = "Get all gates", description = "Retrieve a list of all gates for the current tenant.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of gates returned successfully"),
            @ApiResponse(responseCode = "403", description = "Tenant validation failed")
    })
    @Cacheable("gates") // optional, if gates don't change often
    public ResponseEntity<List<GateResponseDTO>> getAllGates(HttpServletRequest request) {
        if (!TenantContext.hasTenant()) {
            String tenantFromHeader = request.getHeader("X-Tenant-ID");
            if (tenantFromHeader == null) {
                throw new IllegalArgumentException("Missing X-Tenant-ID header");
            }
            TenantContext.setTenantId(tenantFromHeader);
        }
        TenantUtil.validateTenantFromContext();

        List<Gate> gates = gateService.getAllGates(TenantContext.getTenantId());
        List<GateResponseDTO> response = gates.stream()
                .map(gateService::mapToResponseDTO)
                .toList();
        return ResponseEntity.ok(response);
    }

    /**
     * Create a new gate for the current tenant.
     *
     * Restricted to users with ADMIN role.
     * Tenant is validated using JWT against context.
     *
     * @param gateDTO Data transfer object for gate
     * @param request HTTP request containing X-Tenant-ID (extracted from JWT)
     * @return Created gate as GateResponseDTO
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create a new gate", description = "Create a new gate for the current tenant. Admin only.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Gate created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "403", description = "Tenant validation failed")
    })
    public ResponseEntity<GateResponseDTO> createGate(
            @RequestBody
            @Parameter(description = "Gate data transfer object") GateDTO gateDTO,
            @RequestHeader("X-Tenant-ID")
            @Parameter(description = "Tenant ID from JWT") String jwtTenantId) {
        TenantUtil.validateTenant(jwtTenantId);

        Gate createdGate = gateService.createGate(gateDTO, TenantContext.getTenantId());
        return ResponseEntity.ok(gateService.mapToResponseDTO(createdGate));
    }

    /**
     * Delete a gate by ID for the current tenant.
     *
     * Restricted to users with ADMIN role.
     * Tenant is validated using JWT against context.
     *
     * @param id ID of the gate to delete
     * @param jwtTenantId tenant ID extracted from JWT
     * @return No content on success
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete a gate", description = "Delete a specific gate by ID for the current tenant. Admin only.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Gate deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Gate not found"),
            @ApiResponse(responseCode = "403", description = "Tenant validation failed")
    })
    public ResponseEntity<Void> deleteGate(
            @PathVariable
            @Parameter(description = "ID of the gate to delete") Long id,
            @RequestHeader("X-Tenant-ID")
            @Parameter(description = "Tenant ID from JWT") String jwtTenantId) {
        TenantUtil.validateTenant(jwtTenantId);
        gateService.deleteGate(id, TenantContext.getTenantId());
        return ResponseEntity.noContent().build();
    }
}
