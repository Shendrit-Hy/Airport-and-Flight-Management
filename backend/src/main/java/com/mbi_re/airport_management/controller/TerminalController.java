package com.mbi_re.airport_management.controller;

import com.mbi_re.airport_management.dto.TerminalDTO;
import com.mbi_re.airport_management.service.TerminalService;
import com.mbi_re.airport_management.utils.TenantUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing airport terminals.
 * <p>
 * This controller provides endpoints for retrieving and creating terminal data,
 * while enforcing tenant isolation using X-Tenant-ID and/or JWT-based tenant validation.
 */
@RestController
@RequestMapping("/api/terminals")
@Tag(name = "Terminal", description = "API endpoints for managing airport terminals")
public class TerminalController {

    private final TerminalService terminalService;

    public TerminalController(TerminalService terminalService) {
        this.terminalService = terminalService;
    }

    /**
     * Retrieves all terminals for a given tenant.
     * <p>
     * This endpoint does not require authentication, but the tenant must be validated using the {@code X-Tenant-ID} header.
     * Useful for public-facing pages or information access without login.
     * </p>
     *
     * @param tenantId the tenant ID from the request header
     * @return list of terminal DTOs associated with the tenant
     */
    @Operation(
            summary = "Get all terminals",
            description = "Returns a list of all terminals associated with the provided tenant ID.",
            parameters = {
                    @Parameter(name = "X-Tenant-ID", description = "Tenant ID from the request header", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved terminals",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = TerminalDTO.class))),
                    @ApiResponse(responseCode = "403", description = "Forbidden due to tenant validation failure",
                            content = @Content)
            }
    )
    @GetMapping
    @Cacheable(value = "terminals", key = "#tenantId")
    public List<TerminalDTO> getAllTerminals(
            @Parameter(description = "Tenant ID from the request header", required = true)
            @RequestHeader("X-Tenant-ID") String tenantId
    ) {
        TenantUtil.validateTenant(tenantId);
        return terminalService.getAllTerminals(tenantId);
    }

    /**
     * Creates a new terminal under the current tenant's context.
     * <p>
     * Requires authentication with the "ADMIN" role and a valid tenant derived from the JWT token.
     * This method ensures that the tenant in the JWT matches the expected tenant context.
     * </p>
     *
     * @param dto                the terminal data to create
     * @param tenantIdFromHeader the tenant ID from the request header (used for validation and logging)
     * @param jwtTenantId        the tenant ID extracted from the authenticated user's JWT
     * @return the created TerminalDTO object
     */
    @Operation(
            summary = "Create terminal",
            description = "Creates a new terminal under the current tenant. Only accessible to ADMINs.",
            security = @SecurityRequirement(name = "bearerAuth"),
            parameters = {
                    @Parameter(name = "X-Tenant-ID", description = "Tenant ID from the request header", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Terminal created successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = TerminalDTO.class))),
                    @ApiResponse(responseCode = "403", description = "Forbidden due to tenant mismatch or unauthorized access",
                            content = @Content)
            }
    )
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public TerminalDTO createTerminal(
            @Parameter(description = "Terminal data to create", required = true)
            @RequestBody TerminalDTO dto,
            @Parameter(description = "Tenant ID from request header", required = true)
            @RequestHeader("X-Tenant-ID") String tenantIdFromHeader,
            @Parameter(description = "Tenant ID extracted from JWT token", required = true)
            @RequestAttribute(name = "jwtTenantId", required = false) String jwtTenantId
    ) {
        TenantUtil.validateTenant(jwtTenantId);
        return terminalService.createTerminal(dto, jwtTenantId);
    }

    /**
     * Deletes an existing terminal by its ID under the current tenant context.
     * <p>
     * Tenant ID is validated from the {@code X-Tenant-ID} header.
     * This operation is transactional and returns HTTP 204 on success.
     * </p>
     *
     * @param terminalId the ID of the terminal to delete
     * @param tenantId   the tenant ID from the request header
     * @return ResponseEntity with HTTP 204 status on successful deletion
     */
    @Operation(
            summary = "Delete terminal",
            description = "Deletes a terminal by ID for the specified tenant.",
            parameters = {
                    @Parameter(name = "terminalId", description = "ID of the terminal to delete", required = true),
                    @Parameter(name = "X-Tenant-ID", description = "Tenant ID from the request header", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "204", description = "Terminal deleted successfully"),
                    @ApiResponse(responseCode = "403", description = "Forbidden due to tenant validation failure or unauthorized access",
                            content = @Content),
                    @ApiResponse(responseCode = "404", description = "Terminal not found",
                            content = @Content)
            }
    )
    @DeleteMapping("/{terminalId}")
    @Transactional
    public ResponseEntity<Void> deleteTerminal(
            @Parameter(description = "ID of the terminal to delete", required = true)
            @PathVariable Long terminalId,
            @Parameter(description = "Tenant ID from the request header", required = true)
            @RequestHeader("X-Tenant-ID") String tenantId) {
        terminalService.deleteTerminal(terminalId, tenantId);
        return ResponseEntity.noContent().build();
    }
}
