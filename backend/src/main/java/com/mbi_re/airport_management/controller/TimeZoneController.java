package com.mbi_re.airport_management.controller;

import com.mbi_re.airport_management.dto.TimeZoneDTO;
import com.mbi_re.airport_management.service.TimeZoneService;
import com.mbi_re.airport_management.utils.TenantUtil;
import com.mbi_re.airport_management.config.TenantContext;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing time zones in the system.
 * <p>
 * This controller provides an endpoint to fetch all time zones
 * available for the current tenant. It supports both authenticated
 * and unauthenticated requests:
 * </p>
 * <ul>
 *     <li><b>Authenticated</b>: Tenant is validated from the JWT (via TenantContext).</li>
 *     <li><b>Unauthenticated</b>: Tenant must be provided via the {@code X-Tenant-ID} header.</li>
 * </ul>
 */
@RestController
@RequestMapping("/api/timezones")
@Tag(name = "TimeZone", description = "Time zone API for fetching supported time zones.")
public class TimeZoneController {

    private final TimeZoneService timeZoneService;

    @Autowired
    public TimeZoneController(TimeZoneService timeZoneService) {
        this.timeZoneService = timeZoneService;
    }

    /**
     * Retrieves a list of all supported time zones for the current tenant.
     * <p>
     * This endpoint may be accessed by:
     * </p>
     * <ul>
     *     <li>Authenticated users (JWT token with tenant info)</li>
     *     <li>Unauthenticated clients providing {@code X-Tenant-ID} in the request header</li>
     * </ul>
     *
     * @param tenantHeader Optional {@code X-Tenant-ID} header for unauthenticated requests.
     *                     If present, it must match the tenant from the current context.
     * @return A {@link ResponseEntity} containing the list of {@link TimeZoneDTO}s
     *         or a 403 Forbidden response if tenant validation fails.
     */
    @Operation(
            summary = "Get all time zones",
            description = "Returns a list of all time zones available for the current tenant. " +
                    "Accessible to both authenticated and unauthenticated users. Unauthenticated users must provide the 'X-Tenant-ID' header.",
            parameters = {
                    @Parameter(
                            name = "X-Tenant-ID",
                            description = "Tenant ID for unauthenticated access",
                            required = false
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of time zones retrieved successfully.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = TimeZoneDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden due to invalid or mismatched tenant ID.",
                            content = @Content
                    )
            }
    )
    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Cacheable("timezones")
    public ResponseEntity<List<TimeZoneDTO>> getAllTimeZones(
            @RequestHeader(value = "X-Tenant-ID", required = false)
            @Parameter(description = "Tenant ID for unauthenticated access", example = "tenant123")
            String tenantHeader
    ) {
        if (tenantHeader != null) {
            if (!tenantHeader.equalsIgnoreCase(TenantContext.getTenantId())) {
                return ResponseEntity.status(403).build();
            }
        } else {
            String jwtTenant = TenantContext.getTenantId();
            TenantUtil.validateTenant(jwtTenant);
        }

        return ResponseEntity.ok(timeZoneService.getAllTimeZones());
    }
}
