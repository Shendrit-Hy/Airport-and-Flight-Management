package com.mbi_re.airport_management.controller;

import com.mbi_re.airport_management.dto.SupportDTO;
import com.mbi_re.airport_management.model.Support;
import com.mbi_re.airport_management.service.SupportService;
import com.mbi_re.airport_management.utils.TenantUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing general support tickets within a tenant context.
 * Allows users to submit support requests and provides admin-only access
 * for viewing and deleting support tickets.
 */
@RestController
@RequestMapping("/api/support")
@Tag(name = "Support Controller", description = "Handles general support tickets for each tenant")
public class SupportController {

    private final SupportService service;

    public SupportController(SupportService service) {
        this.service = service;
    }

    /**
     * Submit a general support request for the current tenant.
     * <p>
     * This endpoint is publicly accessible and does not require authentication.
     * Tenant validation is done based on the provided tenant ID header and current context.
     * </p>
     *
     * @param request  The support request payload containing issue details.
     * @param tenantId The tenant ID passed in the request header for validation.
     * @return The saved Support entity with generated ID and tenant info.
     */
    @Operation(
            summary = "Submit support request",
            description = "Public endpoint for submitting general support tickets. No authentication required.",
            parameters = {
                    @Parameter(name = "X-Tenant-ID", description = "Tenant ID from the request header", required = true)
            }
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Support request submitted successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request payload"),
            @ApiResponse(responseCode = "403", description = "Missing or mismatched tenant context")
    })
    @PostMapping
    public ResponseEntity<Support> fileComplaint(
            @Parameter(description = "Support request payload", required = true)
            @RequestBody SupportDTO request,
            @Parameter(description = "Tenant ID from request header", required = true)
            @RequestHeader("X-Tenant-ID") String tenantId) {
        TenantUtil.validateTenantFromContext(); // Validates tenant consistency with interceptor
        request.setTenantId(tenantId);
        Support saved = service.fileComplaint(request);
        return ResponseEntity.ok(saved);
    }

    /**
     * Retrieve all submitted support tickets for the authenticated tenant.
     * <p>
     * Requires ADMIN role and tenant ID extracted from JWT token.
     * Results are cached per tenant for performance.
     * </p>
     *
     * @param jwtTenantId The tenant ID extracted from JWT token (request attribute).
     * @return List of support tickets submitted for the tenant.
     */
    @Operation(
            summary = "Get all support tickets",
            description = "Returns all submitted support complaints for the current tenant. ADMIN role required.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Support complaints retrieved successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied for non-admin users or tenant mismatch"),
            @ApiResponse(responseCode = "401", description = "JWT token missing or invalid")
    })
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Cacheable(value = "support_tickets", key = "#jwtTenantId")
    public ResponseEntity<List<Support>> getAll(
            @Parameter(description = "Tenant ID extracted from JWT token", required = true)
            @RequestAttribute("jwtTenantId") String jwtTenantId) {
        TenantUtil.validateTenant(jwtTenantId);
        List<Support> tickets = service.getAllComplaints(jwtTenantId);
        return ResponseEntity.ok(tickets);
    }

    /**
     * Delete a specific support ticket by ID for the authenticated tenant.
     * <p>
     * Requires ADMIN role and validates the tenant context.
     * </p>
     *
     * @param id          The ID of the support complaint to delete.
     * @param jwtTenantId The tenant ID extracted from JWT token (request attribute).
     */
    @Operation(
            summary = "Delete support ticket",
            description = "Deletes a specific support ticket by ID. ADMIN role required.",
            security = @SecurityRequirement(name = "bearerAuth"),
            parameters = {
                    @Parameter(name = "id", description = "ID of the support ticket to delete", required = true)
            }
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Support ticket deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Support ticket not found"),
            @ApiResponse(responseCode = "403", description = "Access denied for non-admin users or tenant mismatch"),
            @ApiResponse(responseCode = "401", description = "JWT token missing or invalid")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(
            @Parameter(description = "ID of the support ticket to delete", required = true)
            @PathVariable Long id,
            @Parameter(description = "Tenant ID extracted from JWT token", required = true)
            @RequestAttribute("jwtTenantId") String jwtTenantId) {
        TenantUtil.validateTenant(jwtTenantId);
        service.deleteSupport(id, jwtTenantId);
    }
}
