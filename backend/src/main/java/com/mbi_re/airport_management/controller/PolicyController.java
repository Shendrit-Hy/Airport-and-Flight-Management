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

@RestController
@RequestMapping("/api/policies")
@Tag(name = "Policy Controller", description = "API endpoints for managing policies per tenant.")
public class PolicyController {

    private final PolicyService policyService;

    public PolicyController(PolicyService policyService) {
        this.policyService = policyService;
    }

    @Operation(
            summary = "Get all policies",
            description = "Fetches all policies associated with the authenticated tenant. Requires USER or ADMIN role."
    )
    @GetMapping
    public ResponseEntity<List<Policy>> getAllPolicies(
            @RequestHeader("X-Tenant-ID") String tenantId) {
        TenantUtil.validateTenant(tenantId);
        System.out.println(tenantId);
        return ResponseEntity.ok(policyService.getAllPolicies(tenantId));
    }

    @Operation(
            summary = "Create a new policy",
            description = "Creates a new policy entry. Requires ADMIN role and tenant must match JWT."
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Policy> createPolicy(
            @RequestHeader("X-Tenant-ID") String tenantId,
            @Parameter(description = "Policy details") @RequestBody PolicyDTO policyDTO) {
        TenantUtil.validateTenant(tenantId);
        policyDTO.setTenantId(tenantId); // enforce tenant consistency
        return ResponseEntity.ok(policyService.createPolicy(policyDTO));
    }

    @Operation(
            summary = "Delete a policy",
            description = "Deletes a policy by ID. Requires ADMIN role and tenant must match JWT."
    )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePolicy(
            @RequestHeader("X-Tenant-ID") String tenantId,
            @PathVariable Long id) {
        TenantUtil.validateTenant(tenantId);
        policyService.deletePolicy(id, tenantId);
        return ResponseEntity.ok("Policy deleted successfully.");
    }
}
