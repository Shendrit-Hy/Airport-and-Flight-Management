package com.mbi_re.airport_management.controller;

import com.mbi_re.airport_management.config.TenantContext;
import com.mbi_re.airport_management.dto.FlightDTO;
import com.mbi_re.airport_management.model.Flight;
import com.mbi_re.airport_management.model.FlightStatus;
import com.mbi_re.airport_management.repository.FlightRepository;
import com.mbi_re.airport_management.service.FlightService;
import com.mbi_re.airport_management.utils.TenantUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * REST controller for managing flight-related operations.
 *
 * This controller provides endpoints for:
 * - Retrieving today's and upcoming flights (public access via tenant header).
 * - Viewing all flights (admin access only).
 * - Adding and deleting flights (admin access only).
 * - Filtering flights based on multiple criteria (public access via tenant header).
 *
 * <p>Multi-tenancy is supported via the "X-Tenant-ID" header for unauthenticated access,
 * and via the {@link TenantContext} for authenticated users.</p>
 */
@RestController
@RequestMapping("/api/flights")
@RequiredArgsConstructor
@Tag(name = "Flights", description = "Endpoints for managing flights")
public class FlightController {

    @Autowired
    private FlightService flightService;

    @Autowired
    private FlightRepository flightRepository;

    /**
     * Retrieves flights that are scheduled for today or in the future.
     *
     * <p>This endpoint is accessible without authentication by passing the tenant identifier
     * in the "X-Tenant-ID" request header.</p>
     *
     * @param tenantId The tenant identifier from the request header.
     * @return A list of flight DTOs that represent upcoming flights.
     */
    @GetMapping
    @Operation(summary = "Get today's and upcoming flights", description = "Accessible without login by providing tenant header.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Flights retrieved successfully"),
            @ApiResponse(responseCode = "403", description = "Invalid or missing tenant context")
    })
    public List<FlightDTO> getFlights(
            @RequestHeader("X-Tenant-ID")
            @Parameter(description = "Tenant identifier from request header") String tenantId) {
        TenantUtil.validateTenant(tenantId);
        return flightService.getTodayAndUpcomingFlights(tenantId);
    }

    /**
     * Retrieves all flights for the current authenticated tenant.
     *
     * <p>This endpoint is restricted to users with the ADMIN role.</p>
     *
     * @return A list of all flight DTOs for the current tenant.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    @Operation(summary = "Get all flights", description = "Retrieve all flights for the current tenant. Admin only.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "All flights retrieved successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied or invalid tenant")
    })
    public List<FlightDTO> getAllFlights() {
        TenantUtil.validateTenantFromContext();
        return flightService.getAllFlights(TenantContext.getTenantId());
    }

    /**
     * Adds a new flight for the current tenant.
     *
     * <p>This endpoint is restricted to users with the ADMIN role.</p>
     *
     * @param flightDTO The flight data to be added.
     * @return The added flight DTO as confirmation.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @Operation(summary = "Add a new flight", description = "Add a new flight. Admin only.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Flight added successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    public ResponseEntity<FlightDTO> addFlight(
            @RequestBody
            @Parameter(description = "Flight data transfer object") FlightDTO flightDTO) {
        TenantUtil.validateTenantFromContext();
        flightDTO.setTenantId(TenantContext.getTenantId());
        flightDTO.setFlightStatus(FlightStatus.UNKNOWN);
        FlightDTO saved = flightService.addFlight(flightDTO);
        return ResponseEntity.ok(saved);
    }

    /**
     * Deletes a specific flight by its ID.
     *
     * <p>This endpoint is restricted to users with the ADMIN role.</p>
     *
     * @param id The ID of the flight to delete.
     * @return A 204 No Content response if deletion is successful.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a flight", description = "Delete a flight by ID. Admin only.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Flight deleted successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    public ResponseEntity<Void> deleteFlight(
            @PathVariable
            @Parameter(description = "ID of the flight to delete") Long id) {
        TenantUtil.validateTenantFromContext();
        flightService.deleteFlight(id, TenantContext.getTenantId());
        return ResponseEntity.noContent().build();
    }

    /**
     * Filters flights using the provided criteria such as departure/arrival airport, date range, and available seats.
     *
     * <p>This endpoint is publicly accessible using the "X-Tenant-ID" header.</p>
     *
     * @param tenantId Tenant identifier from the request header.
     * @param from Departure airport code (IATA format recommended).
     * @param to Arrival airport code (IATA format recommended).
     * @param startDate Start date of the search range (format: yyyy-MM-dd).
     * @param endDate End date of the search range (format: yyyy-MM-dd).
     * @param passengers Minimum number of available seats required.
     * @return A list of flights that match the search criteria.
     */
    @GetMapping("/filter")
    public ResponseEntity<List<FlightDTO>> getFilteredFlights(
            @RequestHeader("X-Tenant-ID") String tenantId,
            @RequestParam String from,
            @RequestParam String to,
            @RequestParam String startDate,
            @RequestParam String endDate,
            @RequestParam int passengers
    ) {
        TenantUtil.validateTenant(tenantId);

        try {
            LocalDate start = LocalDate.parse(startDate);
            LocalDate end = LocalDate.parse(endDate);

            List<Flight> flights = flightRepository
                    .findByTenantIdAndDepartureAirportIgnoreCaseAndArrivalAirportIgnoreCaseAndFlightDateBetweenAndAvailableSeatGreaterThanEqual(
                            tenantId, from, to, start, end, passengers
                    );

            // ✅ Convert to DTOs using service mapping
            List<FlightDTO> dtos = flights.stream()
                    .map(flightService::mapToDTO) // ❗ Make `mapToDTO` public in the service
                    .collect(Collectors.toList());

            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.emptyList());
        }
    }

}

