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

/**
 * REST controller for managing airport gates.
 *
 * <p>This controller supports operations to list all gates for a tenant,
 * create a new gate, and delete an existing gate.</p>
 *
 * <p>Multi-tenancy is handled by validating the tenant ID either from the JWT or from
 * the "X-Tenant-ID" request header.</p>
 */
@RestController
@RequestMapping("/api/gates")
@Tag(name = "Gates", description = "Endpoints for managing airport gates")
public class GateController {

    private final GateService gateService;

    public GateController(GateService gateService) {
        this.gateService = gateService;
    }

    /**
     * Retrieves all gates belonging to the current tenant.
     *
     * <p>This endpoint is publicly accessible for unauthenticated users but requires
     * the "X-Tenant-ID" header to identify the tenant context. Tenant ID is validated before processing.</p>
     *
     * <p>Response is cached under the "gates" cache if caching is enabled.</p>
     *
     * @param request The HTTP request object used to extract headers.
     * @return ResponseEntity containing the list of GateResponseDTO objects for the tenant.
     */
    @GetMapping
    @Operation(
            summary = "Get all gates",
            description = "Retrieve a list of all gates for the current tenant. Public access with tenant header."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of gates returned successfully"),
            @ApiResponse(responseCode = "403", description = "Tenant validation failed - unauthorized or missing tenant header"),
            @ApiResponse(responseCode = "400", description = "Bad request - missing tenant header")
    })
    @Cacheable("gates")  // Optional caching if gates rarely change
    public ResponseEntity<List<GateResponseDTO>> getAllGates(
            HttpServletRequest request) {

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
     * Creates a new gate for the current tenant.
     *
     * <p>This endpoint requires the user to have ADMIN role and the tenant ID to be validated
     * via the JWT "X-Tenant-ID" header.</p>
     *
     * @param gateDTO      The GateDTO containing gate data for creation.
     * @param jwtTenantId  Tenant ID extracted from the JWT in the request header.
     * @return ResponseEntity containing the created GateResponseDTO.
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Create a new gate",
            description = "Create a new gate for the current tenant. Admin only."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Gate created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "403", description = "Tenant validation failed or access denied")
    })
    public ResponseEntity<GateResponseDTO> createGate(
            @RequestBody
            @Parameter(description = "Gate data transfer object containing new gate details") GateDTO gateDTO,
            @RequestHeader("X-Tenant-ID")
            @Parameter(description = "Tenant ID extracted from JWT token") String jwtTenantId) {

        TenantUtil.validateTenant(jwtTenantId);

        Gate createdGate = gateService.createGate(gateDTO, TenantContext.getTenantId());
        return ResponseEntity.ok(gateService.mapToResponseDTO(createdGate));
    }

    /**
     * Deletes a gate identified by its ID for the current tenant.
     *
     * <p>This endpoint requires ADMIN role authorization and tenant validation via the
     * JWT "X-Tenant-ID" header.</p>
     *
     * @param id           The unique identifier of the gate to be deleted.
     * @param jwtTenantId  Tenant ID extracted from the JWT in the request header.
     * @return ResponseEntity with HTTP 204 No Content if deletion succeeds.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Delete a gate",
            description = "Delete a specific gate by ID for the current tenant. Admin only."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Gate deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Gate not found"),
            @ApiResponse(responseCode = "403", description = "Tenant validation failed or access denied")
    })
    public ResponseEntity<Void> deleteGate(
            @PathVariable
            @Parameter(description = "ID of the gate to delete") Long id,
            @RequestHeader("X-Tenant-ID")
            @Parameter(description = "Tenant ID extracted from JWT token") String jwtTenantId) {

        TenantUtil.validateTenant(jwtTenantId);
        gateService.deleteGate(id, TenantContext.getTenantId());
        return ResponseEntity.noContent().build();
    }
}
