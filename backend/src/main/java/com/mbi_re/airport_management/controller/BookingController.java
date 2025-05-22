package com.mbi_re.airport_management.controller;

import com.mbi_re.airport_management.dto.BookingDTO;
import com.mbi_re.airport_management.model.Booking;
import com.mbi_re.airport_management.service.BookingService;
import com.mbi_re.airport_management.utils.TenantUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing flight bookings within tenant-based system.
 */
@RestController
@RequestMapping("/api/bookings")
@CrossOrigin
@Tag(name = "Booking Management", description = "Endpoints for managing flight bookings")
public class BookingController {

    private final BookingService service;

    public BookingController(BookingService service) {
        this.service = service;
    }

    /**
     * Create a new booking (accessible by authenticated users).
     *
     * @param dto Booking data
     * @return Created booking
     */
    @Operation(summary = "Create a booking", description = "Authenticated users can book flights.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Booking created successfully"),
            @ApiResponse(responseCode = "403", description = "Missing or invalid tenant")
    })
    @PostMapping
    public ResponseEntity<Booking> createBooking(
            @RequestBody
            @Parameter(description = "Booking data", required = true) BookingDTO dto) {

        TenantUtil.validateTenantFromContext();
        String tenantId = TenantUtil.getCurrentTenant();
        Booking booking = service.createBooking(dto, tenantId);
        return ResponseEntity.ok(booking);
    }

    /**
     * Get all bookings (admin only).
     *
     * @return List of all bookings
     */
    @Operation(summary = "Get all bookings", description = "Admin-only endpoint to retrieve all bookings.")
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
     * Get booking by ID (admin only).
     *
     * @param id Booking ID
     * @return Booking details
     */
    @Operation(summary = "Get booking by ID", description = "Admin-only endpoint to retrieve booking details.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Booking returned"),
            @ApiResponse(responseCode = "404", description = "Booking not found")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<Booking> getById(
            @PathVariable
            @Parameter(description = "Booking ID", required = true) Long id) {

        TenantUtil.validateTenantFromContext();
        return ResponseEntity.ok(service.getBookingById(id));
    }

    /**
     * Update booking (admin only).
     *
     * @param id  Booking ID
     * @param dto Updated data
     * @return Updated booking
     */
    @Operation(summary = "Update a booking", description = "Admin-only endpoint to update a booking.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Booking updated"),
            @ApiResponse(responseCode = "404", description = "Booking not found")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Booking> update(
            @PathVariable
            @Parameter(description = "Booking ID", required = true) Long id,
            @RequestBody
            @Parameter(description = "Updated booking data", required = true) BookingDTO dto) {

        TenantUtil.validateTenantFromContext();
        return ResponseEntity.ok(service.updateBooking(id, dto));
    }

    /**
     * Delete a booking (admin only).
     *
     * @param id Booking ID
     * @return 204 No Content
     */
    @Operation(summary = "Delete a booking", description = "Admin-only endpoint to delete a booking.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Booking deleted"),
            @ApiResponse(responseCode = "404", description = "Booking not found")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable
            @Parameter(description = "Booking ID", required = true) Long id) {

        TenantUtil.validateTenantFromContext();
        service.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }
}
