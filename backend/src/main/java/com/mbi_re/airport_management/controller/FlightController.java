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
 * <p>This controller supports:</p>
 * <ul>
 *     <li>Retrieving today's and upcoming flights (public access with tenant header)</li>
 *     <li>Viewing all flights (admin access only)</li>
 *     <li>Adding and deleting flights (admin access only)</li>
 *     <li>Filtering flights based on various criteria (public access with tenant header)</li>
 * </ul>
 *
 * <p>Multi-tenancy is supported via the "X-Tenant-ID" header for unauthenticated requests
 * and via {@link TenantContext} for authenticated users.</p>
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
     * Retrieves flights scheduled for today and future dates.
     * <p>This endpoint is accessible without authentication by providing the tenant ID in the "X-Tenant-ID" header.</p>
     *
     * @param tenantId Tenant identifier from the request header.
     * @return List of upcoming flight DTOs.
     */
    @GetMapping
    @Operation(
            summary = "Get today's and upcoming flights",
            description = "Retrieve flights scheduled for today and future dates. Accessible without login by providing tenant header."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Flights retrieved successfully"),
            @ApiResponse(responseCode = "403", description = "Invalid or missing tenant context")
    })
    public List<FlightDTO> getFlights(
            @RequestHeader("X-Tenant-ID")
            @Parameter(description = "Tenant identifier from request header", required = true) String tenantId) {

        TenantUtil.validateTenant(tenantId);
        return flightService.getTodayAndUpcomingFlights(tenantId);
    }

    /**
     * Retrieves all flights for the authenticated tenant.
     * <p>This endpoint is restricted to users with ADMIN role.</p>
     *
     * @return List of all flight DTOs for the current tenant.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    @Operation(
            summary = "Get all flights",
            description = "Retrieve all flights for the current tenant. Admin access only."
    )
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
     * <p>This endpoint is restricted to users with ADMIN role.</p>
     *
     * @param flightDTO Flight data transfer object containing flight details.
     * @return The saved flight DTO.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @Operation(
            summary = "Add a new flight",
            description = "Create a new flight record. Admin access only."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Flight added successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    public ResponseEntity<FlightDTO> addFlight(
            @RequestBody
            @Parameter(description = "Flight data transfer object", required = true) FlightDTO flightDTO) {

        TenantUtil.validateTenantFromContext();

        // Assign tenant and default flight status
        flightDTO.setTenantId(TenantContext.getTenantId());
        flightDTO.setFlightStatus(FlightStatus.UNKNOWN);

        FlightDTO saved = flightService.addFlight(flightDTO);
        return ResponseEntity.ok(saved);
    }

    /**
     * Deletes a flight by its ID.
     * <p>This endpoint is restricted to users with ADMIN role.</p>
     *
     * @param id The ID of the flight to delete.
     * @return HTTP 204 No Content on successful deletion.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a flight",
            description = "Delete a flight by ID. Admin access only."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Flight deleted successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    public ResponseEntity<Void> deleteFlight(
            @PathVariable
            @Parameter(description = "ID of the flight to delete", required = true) Long id) {

        TenantUtil.validateTenantFromContext();
        flightService.deleteFlight(id, TenantContext.getTenantId());
        return ResponseEntity.noContent().build();
    }

    /**
     * Filters flights by departure airport, arrival airport, date range, and minimum available seats.
     * <p>This endpoint is publicly accessible with the tenant ID provided in the "X-Tenant-ID" header.</p>
     *
     * @param tenantId   Tenant identifier from the request header.
     * @param from       Departure airport code (IATA format recommended).
     * @param to         Arrival airport code (IATA format recommended).
     * @param startDate  Start date of the search range (ISO format: yyyy-MM-dd).
     * @param endDate    End date of the search range (ISO format: yyyy-MM-dd).
     * @param passengers Minimum number of available seats required.
     * @return List of flight DTOs matching the filter criteria or empty list on invalid input.
     */
    @GetMapping("/filter")
    @Operation(
            summary = "Filter flights",
            description = "Search flights by departure/arrival airports, date range, and available seats. Public access with tenant header."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Filtered flights retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters")
    })
    public ResponseEntity<List<FlightDTO>> getFilteredFlights(
            @RequestHeader("X-Tenant-ID")
            @Parameter(description = "Tenant identifier from request header", required = true) String tenantId,
            @RequestParam
            @Parameter(description = "Departure airport code (IATA format)", required = true) String from,
            @RequestParam
            @Parameter(description = "Arrival airport code (IATA format)", required = true) String to,
            @RequestParam
            @Parameter(description = "Start date (yyyy-MM-dd)", required = true) String startDate,
            @RequestParam
            @Parameter(description = "End date (yyyy-MM-dd)", required = true) String endDate,
            @RequestParam
            @Parameter(description = "Minimum available seats", required = true) int passengers) {

        TenantUtil.validateTenant(tenantId);

        try {
            LocalDate start = LocalDate.parse(startDate);
            LocalDate end = LocalDate.parse(endDate);

            List<Flight> flights = flightRepository
                    .findByTenantIdAndDepartureAirportIgnoreCaseAndArrivalAirportIgnoreCaseAndFlightDateBetweenAndAvailableSeatGreaterThanEqual(
                            tenantId, from, to, start, end, passengers
                    );

            List<FlightDTO> dtos = flights.stream()
                    .map(flightService::mapToDTO) // Ensure this method is public in FlightService
                    .collect(Collectors.toList());

            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            // Bad input format or other error
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.emptyList());
        }
    }
}
