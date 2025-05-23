package com.mbi_re.airport_management.controller;

import com.mbi_re.airport_management.dto.PassengerDTO;
import com.mbi_re.airport_management.model.Passenger;
import com.mbi_re.airport_management.service.PassengerService;
import com.mbi_re.airport_management.utils.TenantUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controller to manage passenger data within tenant scope.
 * <p>
 * Supports operations to create, retrieve, update, and delete passengers.
 * Tenant validation is enforced either via JWT tenant ID or header-based tenant ID.
 * Access control is role-based, mostly restricted to ADMIN users.
 * </p>
 */
@RestController
@RequestMapping("/api/passengers")
@CrossOrigin
@Tag(name = "Passenger Management", description = "Endpoints for managing passengers")
public class PassengerController {

    private final PassengerService passengerService;

    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    /**
     * Retrieves all passengers for the authenticated tenant.
     *
     * <p>Accessible only by users with ADMIN role. Tenant ID is extracted from JWT token.</p>
     *
     * @param jwtTenantId the tenant ID from JWT token, injected by security filter
     * @return a list of all passengers belonging to the tenant
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Get all passengers",
            description = "Fetch all passengers for the authenticated tenant (ADMIN only)."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of passengers"),
            @ApiResponse(responseCode = "403", description = "Forbidden - invalid or missing tenant ID")
    })
    @Cacheable(value = "passengers", key = "#jwtTenantId")
    public List<Passenger> getAll(
            @RequestAttribute("jwtTenantId")
            @Parameter(description = "Tenant ID extracted from JWT token", required = true)
            String jwtTenantId) {
        TenantUtil.validateTenant(jwtTenantId);
        return passengerService.getAllByTenantId(jwtTenantId);
    }

    /**
     * Updates an existing passenger's details.
     *
     * <p>Accessible only by users with ADMIN role. Validates tenant from JWT.</p>
     *
     * @param id          the ID of the passenger to update
     * @param updated     the passenger data to update
     * @param jwtTenantId the tenant ID from JWT token
     * @return the updated Passenger entity
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Update passenger",
            description = "Update a passenger's details (ADMIN only)."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Passenger updated successfully"),
            @ApiResponse(responseCode = "403", description = "Forbidden - invalid tenant or unauthorized"),
            @ApiResponse(responseCode = "404", description = "Passenger not found")
    })
    public ResponseEntity<Passenger> update(
            @PathVariable
            @Parameter(description = "ID of the passenger to update", required = true)
            Long id,
            @RequestBody
            @Parameter(description = "Updated passenger data", required = true)
            PassengerDTO updated,
            @RequestAttribute("jwtTenantId")
            @Parameter(description = "Tenant ID extracted from JWT token", required = true)
            String jwtTenantId) {
        TenantUtil.validateTenant(jwtTenantId);
        Passenger passenger = passengerService.update(id, updated, jwtTenantId);
        return ResponseEntity.ok(passenger);
    }

    /**
     * Creates a new passenger for the specified tenant.
     *
     * <p>Allows unauthenticated users to create a passenger by providing tenant ID in header.</p>
     *
     * @param passengerDTO the data for the new passenger
     * @param tenantId     the tenant ID from the "X-Tenant-ID" header
     * @return the created PassengerDTO
     */
    @PostMapping
    @Operation(
            summary = "Create passenger",
            description = "Create a new passenger for a given tenant using X-Tenant-ID header."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Passenger created successfully"),
            @ApiResponse(responseCode = "403", description = "Forbidden - invalid tenant context")
    })
    public ResponseEntity<PassengerDTO> savePassenger(
            @RequestBody
            @Parameter(description = "Passenger data to create", required = true)
            PassengerDTO passengerDTO,
            @RequestHeader("X-Tenant-ID")
            @Parameter(description = "Tenant ID from request header", required = true)
            String tenantId) {
        TenantUtil.validateTenantFromContext(); // Context set by TenantInterceptor
        passengerDTO.setTenantId(tenantId);
        PassengerDTO savedPassenger = passengerService.savePassenger(passengerDTO);
        return ResponseEntity.ok(savedPassenger);
    }

    /**
     * Deletes a passenger by their ID.
     *
     * <p>Accessible only by ADMIN role. Tenant ID validated from JWT token.</p>
     *
     * @param id          the ID of the passenger to delete
     * @param jwtTenantId the tenant ID from JWT token
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    @Operation(
            summary = "Delete passenger",
            description = "Delete a passenger by ID (ADMIN only)."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Passenger deleted successfully"),
            @ApiResponse(responseCode = "403", description = "Forbidden - unauthorized"),
            @ApiResponse(responseCode = "404", description = "Passenger not found")
    })
    public void delete(
            @PathVariable
            @Parameter(description = "ID of the passenger to delete", required = true)
            Long id,
            @RequestAttribute("jwtTenantId")
            @Parameter(description = "Tenant ID extracted from JWT token", required = true)
            String jwtTenantId) {
        TenantUtil.validateTenant(jwtTenantId);
        passengerService.deleteById(id, jwtTenantId);
    }
}
