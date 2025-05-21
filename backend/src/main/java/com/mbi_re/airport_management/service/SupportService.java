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
    private final EmailService emailService;

    @Autowired
    public SupportService(SupportRepository supportRepository, FlightRepository flightRepository, EmailService emailService) {
        this.supportRepository = supportRepository;
        this.flightRepository = flightRepository;
        this.emailService = emailService;
    }

    /**
     * Creates and saves a new support ticket.
     * Can be used by unauthenticated users using X-Tenant-ID header.
     *
     * @param dto support request data
     * @return saved support entity
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
     *
     * @param tenantId tenant identifier (from JWT)
     * @return list of support tickets
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
     * Deletes a support ticket by ID and tenant.
     *
     * @param supportId ID of the support ticket
     * @param tenantId  tenant ID (from JWT)
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
     * Generates a unique ticket ID.
     *
     * @return 10-character unique ticket identifier
     */
    private String generateTicketId() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 10);
    }
}