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
 */
@RestController
@RequestMapping("/api/seats")
@RequiredArgsConstructor
@Tag(name = "Seats", description = "Operations related to seat availability, visibility, and status")
public class SeatController {

    @Autowired
    private SeatService seatService;

    /**
     * Retrieve a list of available seats for a given flight.
     * This endpoint is accessible without authentication.
     * It validates tenant ID using the request header (X-Tenant-ID).
     *
     * @param tenantId Tenant ID from the request header.
     * @param flightId ID of the flight for which to fetch available seats.
     * @return List of available SeatDTOs.
     */
    @GetMapping("/available/{flightId}")
    @Operation(summary = "Get available seats for a flight", description = "Returns all available (not booked) seats for the specified flight. Public access.")
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
     * Retrieve all seats (both available and unavailable) for a specific flight.
     * This endpoint requires authentication with role USER or ADMIN.
     *
     * @param flightId ID of the flight for which to fetch all seats.
     * @return List of all SeatDTOs for the flight.
     */
    @GetMapping("/all/{flightId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(summary = "Get all seats for a flight", description = "Returns all seats (both available and unavailable) for a specified flight. Requires authentication.")
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
     * Mark a specific seat as unavailable (typically when booked).
     * Only authenticated users with USER or ADMIN role can access this endpoint.
     *
     * @param seatId ID of the seat to mark as unavailable.
     * @return Updated SeatDTO with availability set to false.
     */
    @PutMapping("/{seatId}/unavailable")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(summary = "Mark seat as unavailable", description = "Marks a seat as unavailable (e.g., when it's booked). Requires authentication.")
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
