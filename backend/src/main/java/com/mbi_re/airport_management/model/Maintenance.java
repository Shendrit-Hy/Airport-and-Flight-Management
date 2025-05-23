package com.mbi_re.airport_management.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * Entiteti {@code Maintenance} përfaqëson një raport për mirëmbajtje në një aeroport.
 * Ai përfshin informacione për problemin, prioritetin, vendndodhjen dhe statusin, si dhe është i lidhur me një tenant të caktuar.
 */
@Entity
@Table(name = "maintenance")
public class Maintenance {

    /** ID unike e raportit të mirëmbajtjes */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Kodi ose ID i aeroportit ku ndodhi problemi */
    private String airportID;

    /** Vendndodhja e saktë brenda aeroportit (p.sh., Terminal A, Pista 2) */
    private String location;

    /** Tipi i problemit (p.sh., Elektrik, HVAC, Rrjet) */
    private String issueType;

    /** Emri i personit që raportoi problemin */
    private String reportedBy;

    /** Prioriteti i problemit (p.sh., HIGH, MEDIUM, LOW) */
    private String priority;

    /** Statusi aktual i çështjes (p.sh., REPORTED, IN_PROGRESS, FIXED) */
    private String status;

    /** Përshkrim i detajuar i problemit ose kërkesës për mirëmbajtje */
    private String description;

    /** Koha kur u raportua problemi, në format ISO 8601 */
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime reportedAt;

    /** ID e tenantit që identifikon organizatën në sistemin multi-tenant */
    @Column(name = "tenant_id", nullable = false)
    private String tenantId;

    // Getters dhe Setters

    /**
     * Merr ID-në e mirëmbajtjes.
     *
     * @return ID si {@code Long}
     */
    public Long getId() {
        return id;
    }

    /**
     * Vendos ID-në e mirëmbajtjes.
     *
     * @param id ID e re
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Merr ID-në e aeroportit të përfshirë.
     *
     * @return kodi i aeroportit
     */
    public String getAirportID() {
        return airportID;
    }

    /**
     * Vendos ID-në e aeroportit të përfshirë.
     *
     * @param airportID kodi i ri i aeroportit
     */
    public void setAirportID(String airportID) {
        this.airportID = airportID;
    }

    /**
     * Merr vendndodhjen e problemit në aeroport.
     *
     * @return vendndodhja si tekst
     */
    public String getLocation() {
        return location;
    }

    /**
     * Vendos vendndodhjen e problemit.
     *
     * @param location vendndodhja e re
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Merr tipin e problemit të raportuar.
     *
     * @return tipi i problemit
     */
    public String getIssueType() {
        return issueType;
    }

    /**
     * Vendos tipin e problemit.
     *
     * @param issueType tipi i ri i problemit
     */
    public void setIssueType(String issueType) {
        this.issueType = issueType;
    }

    /**
     * Merr emrin e personit që raportoi problemin.
     *
     * @return emri i raportuesit
     */
    public String getReportedBy() {
        return reportedBy;
    }

    /**
     * Vendos emrin e raportuesit.
     *
     * @param reportedBy emri i ri i raportuesit
     */
    public void setReportedBy(String reportedBy) {
        this.reportedBy = reportedBy;
    }

    /**
     * Merr prioritetin e problemit.
     *
     * @return prioriteti si tekst
     */
    public String getPriority() {
        return priority;
    }

    /**
     * Vendos prioritetin e problemit.
     *
     * @param priority prioriteti i ri
     */
    public void setPriority(String priority) {
        this.priority = priority;
    }

    /**
     * Merr statusin aktual të problemit.
     *
     * @return statusi si tekst
     */
    public String getStatus() {
        return status;
    }

    /**
     * Vendos statusin aktual të problemit.
     *
     * @param status statusi i ri
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Merr përshkrimin e problemit.
     *
     * @return përshkrimi si tekst
     */
    public String getDescription() {
        return description;
    }

    /**
     * Vendos përshkrimin e problemit.
     *
     * @param description përshkrimi i ri
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Merr kohën kur u raportua problemi.
     *
     * @return data/orari i raportimit
     */
    public LocalDateTime getReportedAt() {
        return reportedAt;
    }

    /**
     * Vendos kohën kur u raportua problemi.
     *
     * @param reportedAt data/orari i ri
     */
    public void setReportedAt(LocalDateTime reportedAt) {
        this.reportedAt = reportedAt;
    }

    /**
     * Merr tenantId-in që identifikon organizatën përkatëse.
     *
     * @return tenantId si tekst
     */
    public String getTenantId() {
        return tenantId;
    }

    /**
     * Vendos tenantId-in që identifikon organizatën përkatëse.
     *
     * @param tenantId tenantId i ri
     */
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
