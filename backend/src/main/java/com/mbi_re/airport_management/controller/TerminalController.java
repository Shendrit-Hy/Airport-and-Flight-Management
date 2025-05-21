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
import org.springframework.security.access.prepost.PreAuthorize;
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
     *
     * @param tenantId the tenant ID from the request header
     * @return list of terminal DTOs
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
     *
     * @param dto               the terminal data to create
     * @param tenantIdFromHeader the tenant ID from the request header (used for validation and logging)
     * @param jwtTenantId       the tenant ID extracted from the authenticated user's JWT
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
            @RequestBody TerminalDTO dto,
            @RequestHeader("X-Tenant-ID") String tenantIdFromHeader,
            @RequestAttribute(name = "jwtTenantId", required = false) String jwtTenantId
    ) {
        TenantUtil.validateTenant(jwtTenantId);
        return terminalService.createTerminal(dto, jwtTenantId);
    }
}
