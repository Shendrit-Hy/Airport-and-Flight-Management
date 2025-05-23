package com.mbi_re.airport_management.controller;

import com.mbi_re.airport_management.dto.AnnouncementDTO;
import com.mbi_re.airport_management.service.AnnouncementService;
import com.mbi_re.airport_management.utils.TenantUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing announcements within a multi-tenant environment.
 * Tenant context must be provided via the "X-Tenant-ID" header.
 * Creation requires ADMIN role.
 */
@RestController
@RequestMapping("/api/announcements")
@Tag(name = "Announcements", description = "Endpoints for managing announcements per tenant")
public class AnnouncementController {

    @Autowired
    private AnnouncementService announcementService;

    /**
     * Retrieves announcements for the specified tenant.
     * This endpoint is public (unauthenticated), but requires valid tenant context.
     *
     * @param tenantId Tenant identifier from request header "X-Tenant-ID"
     * @return HTTP 200 with list of AnnouncementDTO for the tenant
     */
    @GetMapping
    @Operation(
            summary = "Get announcements",
            description = "Returns all announcements for the specified tenant. Public endpoint, tenant must be valid."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved announcements",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AnnouncementDTO.class, type = "array"))
            ),
            @ApiResponse(responseCode = "403", description = "Missing or invalid tenant context", content = @Content)
    })
    public ResponseEntity<List<AnnouncementDTO>> getAnnouncements(
            @RequestHeader("X-Tenant-ID")
            @Parameter(description = "Tenant ID in request header", required = true)
            String tenantId) {

        TenantUtil.validateTenantFromContext();
        List<AnnouncementDTO> announcements = announcementService.getAnnouncementsByTenant(tenantId);
        return ResponseEntity.ok(announcements);
    }

    /**
     * Creates a new announcement for the specified tenant.
     * Requires ADMIN role.
     *
     * @param dto      Announcement data transfer object
     * @param tenantId Tenant identifier from request header "X-Tenant-ID"
     * @return HTTP 200 with created AnnouncementDTO
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Create announcement",
            description = "Creates a new announcement for the tenant. Requires ADMIN role."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Announcement created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AnnouncementDTO.class))
            ),
            @ApiResponse(responseCode = "403", description = "Forbidden for non-admin or tenant mismatch", content = @Content)
    })
    public ResponseEntity<AnnouncementDTO> createAnnouncement(
            @RequestBody
            @Parameter(description = "Announcement data", required = true,
                    content = @Content(schema = @Schema(implementation = AnnouncementDTO.class)))
            AnnouncementDTO dto,
            @RequestHeader("X-Tenant-ID")
            @Parameter(description = "Tenant ID in request header", required = true)
            String tenantId) {

        TenantUtil.validateTenant(tenantId);
        dto.setTenantId(tenantId);
        AnnouncementDTO savedAnnouncement = announcementService.saveAnnouncement(dto);
        return ResponseEntity.ok(savedAnnouncement);
    }
}
