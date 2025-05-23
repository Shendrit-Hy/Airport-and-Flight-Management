package com.mbi_re.airport_management.dto;

import java.time.LocalDateTime;

/**
 * DTO (Data Transfer Object) për përfaqësimin e një raporti të mirëmbajtjes
 * në një aeroport të caktuar. Përdoret për transferimin e të dhënave
 * për çështjet e raportuara nga përdoruesit ose stafi teknik.
 */
public class MaintenanceDTO {

    /** ID unike e raportit të mirëmbajtjes (nullable gjatë krijimit) */
    private Long id;

    /** Kodi i aeroportit ku është raportuar problemi */
    private String airportCode;

    /** Lokacioni specifik brenda aeroportit ku është raportuar problemi */
    private String location;

    /** Tipi i çështjes së mirëmbajtjes (p.sh., Elektrike, Siguri) */
    private String issueType;

    /** Emri i personit që ka raportuar problemin */
    private String reportedBy;

    /** Niveli i përparësisë së problemit (p.sh., High, Medium, Low) */
    private String priority;

    /** Statusi aktual i çështjes së mirëmbajtjes (p.sh., Pending, In Progress, Resolved) */
    private String status;

    /** Përshkrimi i detajuar i çështjes së raportuar */
    private String description;

    /** Data dhe ora kur është raportuar çështja */
    private LocalDateTime reportedAt;

    /** ID e tenantit për sistemin multi-tenant */
    private String tenantId;

    /**
     * Merr ID-në e tenantit.
     *
     * @return tenantId si tekst
     */
    public String getTenantId() {
        return tenantId;
    }

    /**
     * Vendos ID-në e tenantit.
     *
     * @param tenantId ID e re e tenantit
     */
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    /**
     * Merr ID-në e raportit të mirëmbajtjes.
     *
     * @return ID si {@code Long}
     */
    public Long getId() {
        return id;
    }

    /**
     * Vendos ID-në e raportit të mirëmbajtjes.
     *
     * @param id ID e re
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Merr kodin e aeroportit ku është raportuar problemi.
     *
     * @return kodi i aeroportit
     */
    public String getAirportCode() {
        return airportCode;
    }

    /**
     * Vendos kodin e aeroportit ku është raportuar problemi.
     *
     * @param airportCode kodi i ri i aeroportit
     */
    public void setAirportCode(String airportCode) {
        this.airportCode = airportCode;
    }

    /**
     * Merr lokacionin specifik të problemit.
     *
     * @return lokacioni
     */
    public String getLocation() {
        return location;
    }

    /**
     * Vendos lokacionin specifik të problemit.
     *
     * @param location lokacioni i ri
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
     * Vendos tipin e problemit të raportuar.
     *
     * @param issueType tipi i ri i problemit
     */
    public void setIssueType(String issueType) {
        this.issueType = issueType;
    }

    /**
     * Merr emrin e personit që ka raportuar problemin.
     *
     * @return emri i raportuesit
     */
    public String getReportedBy() {
        return reportedBy;
    }

    /**
     * Vendos emrin e personit që ka raportuar problemin.
     *
     * @param reportedBy emri i ri i raportuesit
     */
    public void setReportedBy(String reportedBy) {
        this.reportedBy = reportedBy;
    }

    /**
     * Merr përparësinë e problemit.
     *
     * @return përparësia (priority)
     */
    public String getPriority() {
        return priority;
    }

    /**
     * Vendos përparësinë e problemit.
     *
     * @param priority përparësia e re
     */
    public void setPriority(String priority) {
        this.priority = priority;
    }

    /**
     * Merr statusin aktual të problemit.
     *
     * @return statusi
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
     * @return përshkrimi
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
     * Merr datën dhe orën kur është raportuar problemi.
     *
     * @return data dhe ora e raportimit
     */
    public LocalDateTime getReportedAt() {
        return reportedAt;
    }

    /**
     * Vendos datën dhe orën e raportimit.
     *
     * @param reportedAt data dhe ora e re
     */
    public void setReportedAt(LocalDateTime reportedAt) {
        this.reportedAt = reportedAt;
    }
}
