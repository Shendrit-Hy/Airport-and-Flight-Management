package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.dto.SupportDTO;
import com.mbi_re.airport_management.model.Flight;
import com.mbi_re.airport_management.model.Support;
import com.mbi_re.airport_management.repository.FlightRepository;
import com.mbi_re.airport_management.repository.SupportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SupportService {

    private final SupportRepository supportRepository;

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    public SupportService(SupportRepository supportRepository) {
        this.supportRepository = supportRepository;
    }

    @Autowired
    private EmailService emailService;

    public Support fileComplaint(SupportDTO dto) {
        Support support = new Support();
        support.setSubject(dto.getSubject());
        support.setMessage(dto.getMessage());
        support.setEmail(dto.getEmail());
        support.setTenantId(dto.getTenantId());
        support.setCreatedAt(LocalDateTime.now());
        support.setType(dto.getType());
        support.setStatus("open");

        // Generate ticket ID
        String ticketId = generateTicketId();
        support.setTicketId(ticketId);

        // Link to flight if exists
        if (dto.getFlightNumber() != null) {
            Flight flight = flightRepository.findByFlightNumber(dto.getFlightNumber());
            if (flight != null) {
                support.setFlight(flight);
            }
        }

        Support saved = supportRepository.save(support);
        return saved;
    }

    public Support getSupportByTicketId(String ticketId) {
        return supportRepository.findByTicketId(ticketId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket not found"));
    }

    public List<Support> getAllComplaints(String tenantId) {
        return supportRepository.findByTenantId(tenantId);
    }

    private String generateTicketId() {
        return java.util.UUID.randomUUID().toString().replace("-", "").substring(0, 10);
    }

    public void deleteSupport(Long supportId, String tenantId) {
        Support support = supportRepository.findByIdAndTenantId(supportId, tenantId)
                .orElseThrow(() -> new RuntimeException("Support not found or access denied"));
        supportRepository.delete(support);
    }
}

