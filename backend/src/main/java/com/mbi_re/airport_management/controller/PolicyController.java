package com.mbi_re.airport_management.controller;

import com.mbi_re.airport_management.dto.PolicyDTO;
import com.mbi_re.airport_management.model.Policy;
import com.mbi_re.airport_management.service.PolicyService;
import com.mbi_re.airport_management.utils.TenantUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing policies such as terms of service, privacy, etc.
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
     * Retrieves all policies for the current tenant.
     *
     * @return List of tenant-specific policies
     */
    @Operation(
            summary = "Get all policies",
            description = "Fetches all policies associated with the authenticated tenant. Requires USER or ADMIN role."
    )
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<Policy>> getAllPolicies() {
        TenantUtil.validateTenantFromContext(); // Ensure tenant context is set
        return ResponseEntity.ok(policyService.getAllPolicies());
    }

    /**
     * Creates a new policy for the tenant.
     *
     * @param policyDTO Policy data
     * @return The created Policy entity
     */
    @Operation(
            summary = "Create a new policy",
            description = "Creates a new policy entry. Requires ADMIN role and tenant must match JWT."
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Policy> createPolicy(
            @Parameter(description = "Policy details including tenant ID") @RequestBody PolicyDTO policyDTO) {
        TenantUtil.validateTenant(policyDTO.getTenantId());
        return ResponseEntity.ok(policyService.createPolicy(policyDTO));
    }
}
