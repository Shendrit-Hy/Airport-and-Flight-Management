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

@RestController
@RequestMapping("/api/announcements")
@Tag(name = "Announcements", description = "Endpoints for managing announcements per tenant")
public class AnnouncementController {

    @Autowired
    private AnnouncementService announcementService;

    /**
     * Retrieves announcements for the current tenant using X-Tenant-ID header (unauthenticated users).
     *
     * @param tenantId tenant identifier provided via request header
     * @return list of announcements
     */
    @GetMapping
    @Operation(summary = "Get announcements", description = "Returns announcements for the given tenant (public)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved announcements",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "Missing or invalid tenant context")
    })
    public ResponseEntity<List<AnnouncementDTO>> getAnnouncements(
            @RequestHeader("X-Tenant-ID")
            @Parameter(description = "Tenant ID in header", required = true) String tenantId) {

        TenantUtil.validateTenantFromContext(); // Ensures TenantContext was properly set via interceptor
        List<AnnouncementDTO> announcements = announcementService.getAnnouncementsByTenant(tenantId);
        return ResponseEntity.ok(announcements);
    }

    /**
     * Creates a new announcement. Requires user to have ADMIN role.
     *
     * @param dto      announcement data
     * @param tenantId tenant ID extracted from JWT or header
     * @return created announcement
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create announcement", description = "Creates a new announcement (ADMIN only)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Announcement created successfully",
                    content = @Content(schema = @Schema(implementation = AnnouncementDTO.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden for non-admin or tenant mismatch")
    })
    public ResponseEntity<AnnouncementDTO> createAnnouncement(
            @RequestBody
            @Parameter(description = "Announcement data") AnnouncementDTO dto,
            @RequestHeader("X-Tenant-ID")
            @Parameter(description = "Tenant ID in header", required = true) String tenantId) {

        TenantUtil.validateTenant(tenantId); // Ensures tenant from JWT matches header
        dto.setTenantId(tenantId);
        AnnouncementDTO savedAnnouncement = announcementService.saveAnnouncement(dto);
        return ResponseEntity.ok(savedAnnouncement);
    }
}
