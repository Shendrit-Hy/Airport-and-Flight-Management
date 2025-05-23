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
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
 * REST controller for managing Frequently Asked Questions (FAQs).
 * Supports tenant-aware access control and admin-only modifications.
 */
@RestController
@RequestMapping("/api/faqs")
@RequiredArgsConstructor
@Tag(name = "FAQs", description = "Endpoints for managing Frequently Asked Questions (FAQs)")
public class FaqController {

    @Autowired
    private FaqService faqService;

    /**
     * Retrieves all FAQs for the current tenant.
     * <p>
     * - For authenticated users, tenant is validated from JWT context.<br>
     * - For guests, tenant ID must be provided in the X-Tenant-ID header and is validated.
     *
     * @param tenantId optional tenant ID header (required for unauthenticated requests)
     * @param principal the authenticated user principal, if present
     * @return list of FAQs belonging to the current tenant
     */
    @GetMapping
    @Operation(
            summary = "Get FAQs",
            description = "Retrieve all FAQs for the current tenant. Accessible by authenticated and unauthenticated users."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "FAQs retrieved successfully"),
            @ApiResponse(responseCode = "403", description = "Invalid or missing tenant ID")
    })
    public List<FaqDTO> getFaqs(
            @RequestHeader(value = "X-Tenant-ID", required = false)
            @Parameter(description = "Tenant ID from header (required for unauthenticated users)") String tenantId,
            @Parameter(hidden = true) Principal principal) {

        if (principal != null) {
            TenantUtil.validateTenantFromContext();
        } else {
            TenantUtil.validateTenant(tenantId);
        }
        return faqService.getFaqsByTenant(TenantContext.getTenantId());
    }

    /**
     * Creates or updates an FAQ entry.
     * Only users with ADMIN role can access this endpoint.
     *
     * @param faqDTO the FAQ data to be saved or updated
     * @param tenantId tenant ID from header (required if unauthenticated)
     * @param principal authenticated user principal
     * @return the saved FAQ DTO
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Save FAQ",
            description = "Create or update an FAQ. Only accessible by ADMIN users."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "FAQ saved successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied or invalid tenant ID")
    })
    public FaqDTO saveFaq(
            @RequestBody
            @Parameter(description = "FAQ object to be saved", required = true) FaqDTO faqDTO,
            @RequestHeader(value = "X-Tenant-ID", required = false)
            @Parameter(description = "Tenant ID from header (required for unauthenticated users)") String tenantId,
            @Parameter(hidden = true) Principal principal) {

        if (principal != null) {
            TenantUtil.validateTenantFromContext();
        } else {
            TenantUtil.validateTenant(tenantId);
        }

        faqDTO.setTenantId(TenantContext.getTenantId());
        return faqService.saveFaq(faqDTO);
    }

    /**
     * Deletes an FAQ entry by its ID.
     * Only users with ADMIN role can access this endpoint.
     *
     * @param tenantId tenant ID from header (required if unauthenticated)
     * @param id the ID of the FAQ to delete
     * @param principal authenticated user principal
     * @return HTTP 204 No Content if deletion is successful
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Delete FAQ",
            description = "Delete an FAQ by ID. Only accessible by ADMIN users."
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
            @Parameter(description = "ID of the FAQ to delete", required = true) Long id,
            @Parameter(hidden = true) Principal principal) {

        if (principal != null) {
            TenantUtil.validateTenantFromContext();
        } else {
            TenantUtil.validateTenant(tenantId);
        }

        faqService.deleteFaq(id, TenantContext.getTenantId());
        return ResponseEntity.noContent().build();
    }
}
