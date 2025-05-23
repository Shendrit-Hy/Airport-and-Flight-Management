package com.mbi_re.airport_management.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Entiteti {@code Support} përfaqëson një kërkesë për ndihmë ose mbështetje në sistemin e menaxhimit të fluturimeve.
 * Kjo mund të përfshijë feedback, çështje me bagazhet, pyetje teknike apo probleme të tjera të pasagjerëve.
 */
@Entity
@Table(name = "support_requests")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Support {

    /** ID unike e kërkesës për suport, e gjeneruar automatikisht */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** ID unike e biletës së suportit (p.sh., "abc123") */
    private String ticketId;

    /** Subjekti i kërkesës për suport (titulli) */
    private String subject;

    /** Përshkrimi i plotë i kërkesës ose problemi i raportuar */
    private String message;

    /** Email-i i përdoruesit që ka dërguar kërkesën */
    private String email;

    /** Tipi i kërkesës (p.sh., "feedback", "baggage", "technical") */
    private String type;

    /** Statusi aktual i kërkesës (p.sh., "open", "resolved") */
    private String status;

    /** Data dhe ora kur u krijua kërkesa për suport */
    private LocalDateTime createdAt;

    /** Tenant ID për mbështetje multi-tenant */
    private String tenantId;

    /**
     * Fluturimi me të cilin është e lidhur kjo kërkesë (nëse aplikohet).
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flight_id", referencedColumnName = "id")
    private Flight flight;

    // Getters dhe Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }
}
