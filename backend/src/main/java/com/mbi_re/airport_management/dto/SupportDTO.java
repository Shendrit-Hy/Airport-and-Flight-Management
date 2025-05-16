package com.mbi_re.airport_management.dto;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SupportDTO {
    private String ticketId;
    public String subject;
    public String message;
    public String email;
    private String type;
    private String status;
    private LocalDateTime createdAt;
    private String tenantId;
    private String flightNumber;

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getSubject() { return subject; }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    // Getter dhe Setter për message
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    // Getter dhe Setter për email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Getter dhe Setter për tenantId
    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
