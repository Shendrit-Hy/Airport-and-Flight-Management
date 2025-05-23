package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.dto.SupportDTO;
import com.mbi_re.airport_management.model.Flight;
import com.mbi_re.airport_management.model.Support;
import com.mbi_re.airport_management.repository.FlightRepository;
import com.mbi_re.airport_management.repository.SupportRepository;
import com.mbi_re.airport_management.utils.TenantUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Service for handling general support ticket operations.
 */
@Service
@Tag(name = "Support Service", description = "Handles business logic for support tickets.")
public class SupportService {

    private final SupportRepository supportRepository;
    private final FlightRepository flightRepository;

    @Autowired
    public SupportService(SupportRepository supportRepository, FlightRepository flightRepository) {
        this.supportRepository = supportRepository;
        this.flightRepository = flightRepository;
    }

    /**
     * Creates and saves a new support ticket (complaint).
     * This method can be accessed by unauthenticated users by providing the X-Tenant-ID header.
     * Associates the support ticket with a flight if a valid flight number is provided.
     * The cache for support tickets is evicted for the given tenant to maintain consistency.
     *
     * @param dto the support request data transfer object containing tenant ID, subject, message,
     *            email, type, and optionally flight number
     * @return the saved Support entity representing the newly created support ticket
     * @throws IllegalArgumentException if tenant validation fails
     */
    @Operation(summary = "File a complaint", description = "Allows users (even unauthenticated) to file a support ticket using the X-Tenant-ID header.")
    @CacheEvict(value = "support_tickets", key = "#dto.tenantId")
    public Support fileComplaint(
            @Parameter(description = "Support request data with tenant ID, message, and optional flight number") SupportDTO dto) {

        TenantUtil.validateTenant(dto.getTenantId());

        Support support = new Support();
        support.setSubject(dto.getSubject());
        support.setMessage(dto.getMessage());
        support.setEmail(dto.getEmail());
        support.setTenantId(dto.getTenantId());
        support.setCreatedAt(LocalDateTime.now());
        support.setType(dto.getType());
        support.setStatus("open");
        support.setTicketId(generateTicketId());

        if (dto.getFlightNumber() != null) {
            Flight flight = flightRepository.findByFlightNumber(dto.getFlightNumber());
            if (flight != null) {
                support.setFlight(flight);
            }
        }

        return supportRepository.save(support);
    }

    /**
     * Retrieves all support tickets for the authenticated tenant.
     * Access restricted to users with ROLE_USER or ROLE_ADMIN.
     * Results are cached per tenant to optimize performance.
     *
     * @param tenantId the tenant identifier extracted from the JWT token
     * @return list of Support entities representing all support tickets for the tenant
     * @throws IllegalArgumentException if tenant validation fails
     */
    @Operation(summary = "Get all support complaints", description = "Fetches all support tickets for the authenticated tenant. Requires USER or ADMIN role.")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Cacheable(value = "support_tickets", key = "#tenantId")
    public List<Support> getAllComplaints(
            @Parameter(description = "Tenant ID from JWT") String tenantId) {

        TenantUtil.validateTenant(tenantId);
        return supportRepository.findByTenantId(tenantId);
    }

    /**
     * Deletes a support ticket by its ID for the specified tenant.
     * Access restricted to users with ROLE_ADMIN.
     * Evicts the cached support tickets list for the tenant to maintain cache consistency.
     *
     * @param supportId the ID of the support ticket to delete
     * @param tenantId  the tenant identifier extracted from the JWT token
     * @throws ResponseStatusException with 404 status if the support ticket does not exist or does not belong to the tenant
     * @throws IllegalArgumentException if tenant validation fails
     */
    @Operation(summary = "Delete support ticket", description = "Deletes a support ticket by ID for the authenticated tenant. Requires ADMIN role.")
    @PreAuthorize("hasRole('ADMIN')")
    @CacheEvict(value = "support_tickets", key = "#tenantId")
    public void deleteSupport(
            @Parameter(description = "Support ticket ID") Long supportId,
            @Parameter(description = "Tenant ID from JWT") String tenantId) {

        TenantUtil.validateTenant(tenantId);

        Support support = supportRepository.findByIdAndTenantId(supportId, tenantId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Support not found or access denied"));
        supportRepository.delete(support);
    }

    /**
     * Generates a unique 10-character ticket identifier using UUID.
     *
     * @return a unique 10-character ticket ID string
     */
    private String generateTicketId() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 10);
    }
}
