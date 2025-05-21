package com.mbi_re.airport_management.controller;

import com.mbi_re.airport_management.config.TenantContext;
import com.mbi_re.airport_management.dto.FaqDTO;
import com.mbi_re.airport_management.service.FaqService;
import com.mbi_re.airport_management.utils.TenantUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/faqs")
@RequiredArgsConstructor
@Tag(name = "FAQs", description = "Endpoints for managing Frequently Asked Questions (FAQs)")
public class FaqController {

    @Autowired
    private FaqService faqService;

    /**
     * Retrieve all FAQs for the current tenant.
     * If the user is authenticated, tenant is validated using JWT context.
     * If unauthenticated, X-Tenant-ID is used.
     *
     * @param tenantId optional tenant ID header (for guest requests)
     * @param principal the authenticated user, if present
     * @return list of FAQs
     */
    @GetMapping
    @Operation(
            summary = "Get FAQs",
            description = "Retrieve all FAQs for the current tenant. Accessible to all users."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "FAQs retrieved successfully"),
            @ApiResponse(responseCode = "403", description = "Invalid or missing tenant ID")
    })
    public List<FaqDTO> getFaqs(
            @RequestHeader(value = "X-Tenant-ID", required = false)
            @Parameter(description = "Tenant ID from header (required for unauthenticated users)") String tenantId,
            Principal principal) {

        if (principal != null) {
            TenantUtil.validateTenantFromContext(); // Authenticated
        } else {
            TenantUtil.validateTenant(tenantId); // Guest
        }

        return faqService.getFaqsByTenant(TenantContext.getTenantId());

        // 💡 Optional: Add @Cacheable("faqs") to faqService.getFaqsByTenant if FAQs rarely change
    }

    /**
     * Save or update an FAQ.
     * Only accessible to users with ADMIN role.
     *
     * @param faqDTO the FAQ to save
     * @param tenantId tenant ID from header
     * @param principal the authenticated user
     * @return saved FAQ
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @Operation(
            summary = "Save FAQ",
            description = "Create or update an FAQ. Only accessible to ADMIN users."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "FAQ saved successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied or invalid tenant ID")
    })
    public FaqDTO saveFaq(
            @RequestBody
            @Parameter(description = "FAQ object to be saved") FaqDTO faqDTO,
            @RequestHeader(value = "X-Tenant-ID", required = false)
            @Parameter(description = "Tenant ID from header (required for unauthenticated users)") String tenantId,
            Principal principal) {

        if (principal != null) {
            TenantUtil.validateTenantFromContext();
        } else {
            TenantUtil.validateTenant(tenantId);
        }

        faqDTO.setTenantId(TenantContext.getTenantId());
        return faqService.saveFaq(faqDTO);
    }

    /**
     * Delete an FAQ by ID.
     * Only accessible to users with ADMIN role.
     *
     * @param tenantId tenant ID from header
     * @param id FAQ ID to delete
     * @param principal the authenticated user
     * @return 204 No Content
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete FAQ",
            description = "Delete an FAQ by ID. Only accessible to ADMIN users."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "FAQ deleted successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied or invalid tenant ID"),
            @ApiResponse(responseCode = "404", description = "FAQ not found")
    })
    public ResponseEntity<Void> deleteFaq(
            @RequestHeader(value = "X-Tenant-ID", required = false)
            @Parameter(description = "Tenant ID from header (required for unauthenticated users)") String tenantId,
            @PathVariable
            @Parameter(description = "ID of the FAQ to delete") Long id,
            Principal principal) {

        if (principal != null) {
            TenantUtil.validateTenantFromContext();
        } else {
            TenantUtil.validateTenant(tenantId);
        }

        faqService.deleteFaq(id, TenantContext.getTenantId());
        return ResponseEntity.noContent().build();
    }
}
