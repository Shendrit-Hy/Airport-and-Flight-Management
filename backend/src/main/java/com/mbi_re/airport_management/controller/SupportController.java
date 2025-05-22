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
     * Submits a general support request for the current tenant.
     * This endpoint is publicly accessible and does not require authentication.
     * The tenant is validated using the header and request context.
     *
     * @param request  the support request payload
     * @param tenantId the tenant ID from request header
     * @return the saved support ticket
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
            @RequestBody SupportDTO request,
            @RequestHeader("X-Tenant-ID") String tenantId) {
        TenantUtil.validateTenantFromContext(); // validates match with TenantInterceptor
        request.setTenantId(tenantId);
        return ResponseEntity.ok(service.fileComplaint(request));
    }

    /**
     * Retrieves all submitted support tickets for the authenticated tenant.
     * Requires ADMIN role and JWT tenant ID validation.
     * Results are cached per tenant.
     *
     * @param jwtTenantId the tenant ID extracted from JWT
     * @return list of support tickets
     */
    @Operation(
            summary = "Get all support tickets",
            description = "Returns a list of all submitted support complaints for the current tenant. Only accessible to ADMIN users.",
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
            @RequestAttribute("jwtTenantId") String jwtTenantId) {
        TenantUtil.validateTenant(jwtTenantId);
        return ResponseEntity.ok(service.getAllComplaints(jwtTenantId));
    }

    /**
     * Deletes a specific support ticket by ID for the authenticated tenant.
     * Requires ADMIN role and valid tenant context.
     *
     * @param id the ID of the support complaint to delete
     * @param jwtTenantId the tenant ID extracted from JWT
     */
    @Operation(
            summary = "Delete support ticket",
            description = "Deletes a specific support ticket by ID. Only accessible to ADMIN users.",
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
            @PathVariable Long id,
            @RequestAttribute("jwtTenantId") String jwtTenantId) {
        TenantUtil.validateTenant(jwtTenantId);
        service.deleteSupport(id, jwtTenantId);
    }
}