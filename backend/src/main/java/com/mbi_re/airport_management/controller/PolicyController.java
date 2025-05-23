package com.mbi_re.airport_management.controller;

import com.mbi_re.airport_management.dto.PolicyDTO;
import com.mbi_re.airport_management.model.Policy;
import com.mbi_re.airport_management.service.PolicyService;
import com.mbi_re.airport_management.utils.TenantUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing policies on a per-tenant basis.
 * <p>
 * All requests require tenant validation. Access control varies by endpoint.
 * </p>
 */
@RestController
@RequestMapping("/api/policies")
@Tag(name = "Policy Controller", description = "API endpoints for managing policies per tenant.")
public class PolicyController {

    private final PolicyService policyService;

    public PolicyController(PolicyService policyService) {
        this.policyService = policyService;
    }

    /**
     * Retrieves all policies associated with the authenticated tenant.
     * <p>
     * Requires USER or ADMIN role to access.
     * </p>
     *
     * @param tenantId the tenant ID extracted from the "X-Tenant-ID" header
     * @return a list of policies belonging to the tenant wrapped in ResponseEntity
     */
    @Operation(
            summary = "Get all policies",
            description = "Fetches all policies associated with the authenticated tenant. Requires USER or ADMIN role."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of policies"),
            @ApiResponse(responseCode = "403", description = "Forbidden - unauthorized or invalid tenant")
    })
    @GetMapping
    public ResponseEntity<List<Policy>> getAllPolicies(
            @Parameter(description = "Tenant ID from X-Tenant-ID header", required = true)
            @RequestHeader("X-Tenant-ID") String tenantId) {
        TenantUtil.validateTenant(tenantId);
        return ResponseEntity.ok(policyService.getAllPolicies(tenantId));
    }

    /**
     * Creates a new policy entry for the authenticated tenant.
     * <p>
     * Requires ADMIN role and tenant ID consistency.
     * </p>
     *
     * @param tenantId  the tenant ID extracted from the "X-Tenant-ID" header
     * @param policyDTO the policy data to create
     * @return the created Policy wrapped in ResponseEntity
     */
    @Operation(
            summary = "Create a new policy",
            description = "Creates a new policy entry. Requires ADMIN role and tenant must match JWT."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Policy created successfully"),
            @ApiResponse(responseCode = "403", description = "Forbidden - unauthorized or invalid tenant"),
            @ApiResponse(responseCode = "400", description = "Invalid policy data")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Policy> createPolicy(
            @Parameter(description = "Tenant ID from X-Tenant-ID header", required = true)
            @RequestHeader("X-Tenant-ID") String tenantId,
            @Parameter(description = "Policy details", required = true)
            @RequestBody PolicyDTO policyDTO) {
        TenantUtil.validateTenant(tenantId);
        policyDTO.setTenantId(tenantId); // enforce tenant consistency
        return ResponseEntity.ok(policyService.createPolicy(policyDTO));
    }

    /**
     * Deletes an existing policy by its ID for the authenticated tenant.
     * <p>
     * Requires ADMIN role and tenant ID consistency.
     * </p>
     *
     * @param tenantId the tenant ID extracted from the "X-Tenant-ID" header
     * @param id       the ID of the policy to delete
     * @return a success message wrapped in ResponseEntity
     */
    @Operation(
            summary = "Delete a policy",
            description = "Deletes a policy by ID. Requires ADMIN role and tenant must match JWT."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Policy deleted successfully"),
            @ApiResponse(responseCode = "403", description = "Forbidden - unauthorized or invalid tenant"),
            @ApiResponse(responseCode = "404", description = "Policy not found")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePolicy(
            @Parameter(description = "Tenant ID from X-Tenant-ID header", required = true)
            @RequestHeader("X-Tenant-ID") String tenantId,
            @Parameter(description = "Policy ID to delete", required = true)
            @PathVariable Long id) {
        TenantUtil.validateTenant(tenantId);
        policyService.deletePolicy(id, tenantId);
        return ResponseEntity.ok("Policy deleted successfully.");
    }
}
