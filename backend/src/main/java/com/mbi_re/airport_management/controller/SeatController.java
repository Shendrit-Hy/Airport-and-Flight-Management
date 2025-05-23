package com.mbi_re.airport_management.controller;

import com.mbi_re.airport_management.dto.SeatDTO;
import com.mbi_re.airport_management.service.SeatService;
import com.mbi_re.airport_management.utils.TenantUtil;
import com.mbi_re.airport_management.config.TenantContext;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing seat-related operations such as availability and booking.
 * <p>
 * Provides endpoints to get available seats, all seats for a flight, and to mark seats as unavailable.
 * Tenant validation is enforced on all methods.
 * </p>
 */
@RestController
@RequestMapping("/api/seats")
@RequiredArgsConstructor
@Tag(name = "Seats", description = "Operations related to seat availability, visibility, and status")
public class SeatController {

    @Autowired
    private SeatService seatService;

    /**
     * Retrieves a list of available seats for the specified flight.
     * This endpoint is public (no authentication required).
     * Tenant ID must be provided via the "X-Tenant-ID" header and is validated.
     *
     * @param tenantId Tenant ID extracted from the request header "X-Tenant-ID"
     * @param flightId Flight ID to fetch available seats for
     * @return List of SeatDTO objects representing available seats
     */
    @GetMapping("/available/{flightId}")
    @Operation(
            summary = "Get available seats for a flight",
            description = "Returns all available (not booked) seats for the specified flight. Public access."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of available seats",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = SeatDTO.class))),
            @ApiResponse(responseCode = "403", description = "Invalid or mismatched tenant ID")
    })
    public List<SeatDTO> getAvailableSeats(
            @Parameter(description = "Tenant ID from header", required = true)
            @RequestHeader("X-Tenant-ID") String tenantId,

            @Parameter(description = "ID of the flight", required = true)
            @PathVariable Long flightId
    ) {
        TenantUtil.validateTenant(tenantId);
        return seatService.getAvailableSeats(flightId, tenantId);
    }

    /**
     * Retrieves all seats (both available and unavailable) for the specified flight.
     * Requires authenticated user with role USER or ADMIN.
     * Tenant ID is retrieved from TenantContext and validated.
     *
     * @param flightId Flight ID to fetch all seats for
     * @return List of SeatDTO objects representing all seats for the flight
     */
    @GetMapping("/all/{flightId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(
            summary = "Get all seats for a flight",
            description = "Returns all seats (both available and unavailable) for a specified flight. Requires authentication."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of all seats",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = SeatDTO.class))),
            @ApiResponse(responseCode = "403", description = "Unauthorized or tenant ID mismatch")
    })
    public List<SeatDTO> getAllSeats(
            @Parameter(description = "ID of the flight", required = true)
            @PathVariable Long flightId
    ) {
        TenantUtil.validateTenant(TenantContext.getTenantId());
        return seatService.getAllSeats(flightId, TenantContext.getTenantId());
    }

    /**
     * Marks a specific seat as unavailable (e.g., when it is booked).
     * Requires authenticated user with role USER or ADMIN.
     * Tenant ID is retrieved from TenantContext and validated.
     *
     * @param seatId ID of the seat to mark as unavailable
     * @return Updated SeatDTO with availability set to false
     */
    @PutMapping("/{seatId}/unavailable")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(
            summary = "Mark seat as unavailable",
            description = "Marks a seat as unavailable (e.g., when it's booked). Requires authentication."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Seat marked as unavailable",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = SeatDTO.class))),
            @ApiResponse(responseCode = "403", description = "Unauthorized or tenant mismatch")
    })
    public SeatDTO markSeatAsUnavailable(
            @Parameter(description = "ID of the seat to mark as unavailable", required = true)
            @PathVariable Long seatId
    ) {
        TenantUtil.validateTenant(TenantContext.getTenantId());
        return seatService.markSeatAsUnavailable(seatId, TenantContext.getTenantId());
    }
}
