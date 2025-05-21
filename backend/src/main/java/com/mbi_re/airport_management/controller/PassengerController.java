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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

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
     * Retrieves all passengers for the current tenant (ADMIN only).
     *
     * @param jwtTenantId tenant ID from JWT set via filter
     * @return list of passengers
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get all passengers", description = "Fetch all passengers for the authenticated tenant (ADMIN only)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of passengers"),
            @ApiResponse(responseCode = "403", description = "Forbidden - invalid or missing tenant ID")
    })
    @Cacheable(value = "passengers", key = "#jwtTenantId")
    public List<Passenger> getAll(@RequestAttribute("jwtTenantId") String jwtTenantId) {
        TenantUtil.validateTenant(jwtTenantId);
        return passengerService.getAllByTenantId(jwtTenantId);
    }

    /**
     * Updates an existing passenger (ADMIN only).
     *
     * @param id          passenger ID
     * @param updated     updated passenger data
     * @param jwtTenantId tenant ID from JWT set via filter
     * @return updated passenger
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update passenger", description = "Update a passenger's details (ADMIN only)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Passenger updated successfully"),
            @ApiResponse(responseCode = "403", description = "Forbidden - invalid tenant or unauthorized"),
            @ApiResponse(responseCode = "404", description = "Passenger not found")
    })
    public ResponseEntity<Passenger> update(
            @PathVariable Long id,
            @RequestBody PassengerDTO updated,
            @RequestAttribute("jwtTenantId") String jwtTenantId) {
        TenantUtil.validateTenant(jwtTenantId);
        return ResponseEntity.ok(passengerService.update(id, updated, jwtTenantId));
    }

    /**
     * Creates a new passenger (unauthenticated clients allowed using header).
     *
     * @param passengerDTO passenger data
     * @param tenantId     tenant ID from header
     * @return created passenger
     */
    @PostMapping
    @Operation(summary = "Create passenger", description = "Create a new passenger for a given tenant using X-Tenant-ID header")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Passenger created successfully"),
            @ApiResponse(responseCode = "403", description = "Forbidden - invalid tenant context")
    })
    public ResponseEntity<PassengerDTO> savePassenger(
            @RequestBody PassengerDTO passengerDTO,
            @RequestHeader("X-Tenant-ID") String tenantId) {
        TenantUtil.validateTenantFromContext(); // set by TenantInterceptor
        passengerDTO.setTenantId(tenantId);
        return ResponseEntity.ok(passengerService.savePassenger(passengerDTO));
    }

    /**
     * Deletes a passenger by ID (ADMIN only).
     *
     * @param id passenger ID
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete passenger", description = "Delete a passenger by ID (ADMIN only)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Passenger deleted successfully"),
            @ApiResponse(responseCode = "403", description = "Forbidden - unauthorized"),
            @ApiResponse(responseCode = "404", description = "Passenger not found")
    })
    public void delete(@PathVariable Long id, @RequestAttribute("jwtTenantId") String jwtTenantId) {
        TenantUtil.validateTenant(jwtTenantId);
        passengerService.deleteById(id, jwtTenantId);
    }
}
