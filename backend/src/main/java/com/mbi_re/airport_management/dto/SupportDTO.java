package com.mbi_re.airport_management.dto;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * DTO (Data Transfer Object) për përfaqësimin e një kërkese për mbështetje nga përdoruesit.
 * Kjo klasë përdoret për të regjistruar dhe menaxhuar biletat e suportit për fluturime apo shërbime të tjera.
 */
@Data
public class SupportDTO {

    /** ID unike e tiketës së suportit */
    private String ticketId;

    /** Subjekti i kërkesës për suport */
    public String subject;

    /** Mesazhi i dërguar nga përdoruesi */
    public String message;

    /** Email-i i përdoruesit që ka dërguar kërkesën për suport */
    public String email;

    /** Lloji i kërkesës për suport (p.sh., teknike, informacion, ankesë) */
    private String type;

    /** Statusi i tiketës (p.sh., Hapur, Në proces, Mbyllur) */
    private String status;

    /** Data dhe ora kur është krijuar tiketa */
    private LocalDateTime createdAt;

    /** ID e tenantit për të mbështetur multi-tenancy */
    private String tenantId;

    /** Numri i fluturimit nëse kërkesa është e lidhur me ndonjë fluturim specifik */
    private String flightNumber;

    /**
     * Merr ID-në e tiketës së suportit.
     *
     * @return ticketId si tekst
     */
    public String getTicketId() {
        return ticketId;
    }

    /**
     * Vendos ID-në e tiketës së suportit.
     *
     * @param ticketId ID e re e tiketës
     */
    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    /**
     * Merr llojin e tiketës.
     *
     * @return tipi i tiketës
     */
    public String getType() {
        return type;
    }

    /**
     * Vendos llojin e tiketës.
     *
     * @param type tipi i ri
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Merr statusin e tiketës.
     *
     * @return statusi si tekst
     */
    public String getStatus() {
        return status;
    }

    /**
     * Vendos statusin e tiketës.
     *
     * @param status statusi i ri
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Merr numrin e fluturimit të lidhur (nëse ka).
     *
     * @return numri i fluturimit
     */
    public String getFlightNumber() {
        return flightNumber;
    }

    /**
     * Vendos numrin e fluturimit të lidhur.
     *
     * @param flightNumber numri i ri i fluturimit
     */
    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    /**
     * Merr datën dhe orën e krijimit të tiketës.
     *
     * @return data dhe ora e krijimit
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Vendos datën dhe orën e krijimit të tiketës.
     *
     * @param createdAt data dhe ora e re
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Merr subjektin e tiketës.
     *
     * @return subjekti si tekst
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Vendos subjektin e tiketës.
     *
     * @param subject subjekti i ri
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * Merr mesazhin e dërguar nga përdoruesi.
     *
     * @return mesazhi si tekst
     */
    public String getMessage() {
        return message;
    }

    /**
     * Vendos mesazhin e tiketës.
     *
     * @param message mesazhi i ri
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Merr email-in e dërguesit të tiketës.
     *
     * @return email-i
     */
    public String getEmail() {
        return email;
    }

    /**
     * Vendos email-in e dërguesit të tiketës.
     *
     * @param email email-i i ri
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Merr tenantId-in për këtë kërkesë për suport.
     *
     * @return tenantId si tekst
     */
    public String getTenantId() {
        return tenantId;
    }

    /**
     * Vendos tenantId-in për këtë kërkesë për suport.
     *
     * @param tenantId tenantId i ri
     */
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
