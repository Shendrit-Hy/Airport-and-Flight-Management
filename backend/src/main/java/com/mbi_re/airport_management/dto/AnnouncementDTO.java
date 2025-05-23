package com.mbi_re.airport_management.dto;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * DTO (Data Transfer Object) për njoftimet që shpallen brenda sistemit.
 * Përdoret për transferimin e të dhënave të njoftimeve midis shtresave të aplikacionit.
 */
@Data
public class AnnouncementDTO {

    /** Titulli i njoftimit */
    private String title;

    /** Përmbajtja (mesazhi) e njoftimit */
    private String message;

    /** ID e tenantit që lidhet me këtë njoftim */
    private String tenantId;

    /** Data dhe ora e krijimit të njoftimit */
    private LocalDateTime createdAt;

    /**
     * Merr titullin e njoftimit.
     *
     * @return titulli i njoftimit
     */
    public String getTitle() {
        return title;
    }

    /**
     * Vendos titullin e njoftimit.
     *
     * @param title titulli i ri
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Merr përmbajtjen e njoftimit.
     *
     * @return mesazhi i njoftimit
     */
    public String getMessage() {
        return message;
    }

    /**
     * Vendos përmbajtjen e njoftimit.
     *
     * @param message mesazhi i ri i njoftimit
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Merr tenantId-in e njoftimit.
     *
     * @return ID e tenantit
     */
    public String getTenantId() {
        return tenantId;
    }

    /**
     * Vendos tenantId-in e njoftimit.
     *
     * @param tenantId ID e re e tenantit
     */
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    /**
     * Merr datën dhe orën e krijimit të njoftimit.
     *
     * @return data dhe ora e krijimit
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Vendos datën dhe orën e krijimit të njoftimit.
     *
     * @param createdAt data dhe ora e re e krijimit
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
