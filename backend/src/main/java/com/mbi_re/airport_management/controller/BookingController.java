package com.mbi_re.airport_management.controller;

import com.mbi_re.airport_management.dto.BookingDTO;
import com.mbi_re.airport_management.model.Booking;
import com.mbi_re.airport_management.security.JwtService;
import com.mbi_re.airport_management.service.BookingService;
import com.mbi_re.airport_management.utils.TenantUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing flight bookings within a tenant-based system.
 */
@RestController
@RequestMapping("/api/bookings")
@CrossOrigin
@Tag(name = "Booking Management", description = "Endpoints for managing flight bookings")
public class BookingController {

    private final BookingService service;

    @Autowired
    private JwtService jwtService;

    public BookingController(BookingService service) {
        this.service = service;
    }

    /**
     * Create a new booking.
     * <p>
     * Accessible by authenticated users.
     *
     * @param dto the booking data transfer object containing booking details
     * @return the created Booking object
     */
    @Operation(
            summary = "Create a booking",
            description = "Allows authenticated users to book flights. Tenant ID is validated from context."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Booking created successfully"),
            @ApiResponse(responseCode = "403", description = "Missing or invalid tenant")
    })
    @PostMapping
    public ResponseEntity<Booking> createBooking(
            @RequestBody
            @Parameter(description = "Booking data", required = true)
            BookingDTO dto) {

        TenantUtil.validateTenantFromContext();
        String tenantId = TenantUtil.getCurrentTenant();
        dto.setCheckedIn(false);
        Booking booking = service.createBooking(dto, tenantId);
        return ResponseEntity.ok(booking);
    }

    /**
     * Retrieve all bookings.
     * <p>
     * Restricted to users with the ADMIN role.
     *
     * @return list of all Booking objects
     */
    @Operation(
            summary = "Get all bookings",
            description = "Admin-only endpoint to retrieve all bookings."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Bookings fetched successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<Booking>> getAll() {
        TenantUtil.validateTenantFromContext();
        return ResponseEntity.ok(service.getAllBookings());
    }

    /**
     * Retrieve a booking by its ID.
     * <p>
     * Restricted to users with the ADMIN role.
     *
     * @param id the booking ID to fetch
     * @return the Booking object with the specified ID
     */
    @Operation(
            summary = "Get booking by ID",
            description = "Admin-only endpoint to retrieve booking details by ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Booking returned successfully"),
            @ApiResponse(responseCode = "404", description = "Booking not found")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<Booking> getById(
            @PathVariable
            @Parameter(description = "Booking ID", required = true)
            Long id) {

        TenantUtil.validateTenantFromContext();
        return ResponseEntity.ok(service.getBookingById(id));
    }

    /**
     * Update an existing booking by its ID.
     * <p>
     * Restricted to users with the ADMIN role.
     *
     * @param id  the booking ID to update
     * @param dto the updated booking data
     * @return the updated Booking object
     */
    @Operation(
            summary = "Update a booking",
            description = "Admin-only endpoint to update a booking by ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Booking updated successfully"),
            @ApiResponse(responseCode = "404", description = "Booking not found")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Booking> update(
            @PathVariable
            @Parameter(description = "Booking ID", required = true)
            Long id,
            @RequestBody
            @Parameter(description = "Updated booking data", required = true)
            BookingDTO dto) {

        TenantUtil.validateTenantFromContext();
        return ResponseEntity.ok(service.updateBooking(id, dto));
    }

    /**
     * Delete a booking by its ID.
     * <p>
     * Restricted to users with the ADMIN role.
     *
     * @param id the booking ID to delete
     * @return HTTP 204 No Content if deletion was successful
     */
    @Operation(
            summary = "Delete a booking",
            description = "Admin-only endpoint to delete a booking by ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Booking deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Booking not found")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable
            @Parameter(description = "Booking ID", required = true)
            Long id) {

        TenantUtil.validateTenantFromContext();
        service.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Update the check-in status of a booking.
     * <p>
     * Accessible by users with the USER role.
     *
     * @param id        the booking ID
     * @param checkedIn boolean flag indicating check-in status (true = checked in)
     * @return the updated Booking if found, or 404 Not Found
     */
    @Operation(
            summary = "Update check-in status",
            description = "Mark a booking as checked-in or not. Accessible by users with USER role."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Check-in status updated successfully"),
            @ApiResponse(responseCode = "404", description = "Booking not found")
    })
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/{id}/checkin")
    public ResponseEntity<Booking> updateCheckInStatus(
            @PathVariable
            @Parameter(description = "Booking ID", required = true)
            Long id,
            @RequestParam
            @Parameter(description = "New check-in status", required = true)
            boolean checkedIn) {

        TenantUtil.validateTenantFromContext();
        return service.updateCheckInStatus(id, checkedIn)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
