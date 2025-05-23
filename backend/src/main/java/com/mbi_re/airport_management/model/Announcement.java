package com.mbi_re.airport_management.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entiteti {@code Announcement} përfaqëson një njoftim publik që mund të shfaqet në sistemin e aeroportit.
 * Përdoret për të komunikuar mesazhe të rëndësishme për përdoruesit ose stafin.
 */
@Entity
@Table(name = "announcements")
public class Announcement {

    /** ID unike e njoftimit, gjeneruar automatikisht */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Titulli i njoftimit */
    private String title;

    /** Mesazhi përmbajtës i njoftimit */
    private String message;

    /** Data dhe ora kur është krijuar njoftimi */
    private LocalDateTime createdAt;

    /** ID e tenantit që posedon këtë njoftim në sistemin multi-tenant */
    private String tenantId;

    /**
     * Merr tenantId-in që i përket këtij njoftimi.
     *
     * @return tenantId si tekst
     */
    public String getTenantId() {
        return tenantId;
    }

    /**
     * Vendos tenantId-in për këtë njoftim.
     *
     * @param tenantId ID e tenantit
     */
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    /**
     * Merr ID-në e njoftimit.
     *
     * @return ID si {@code Long}
     */
    public Long getId() {
        return id;
    }

    /**
     * Vendos ID-në e njoftimit.
     *
     * @param id ID e re
     */
    public void setId(Long id) {
        this.id = id;
    }

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
     * Merr përmbajtjen e mesazhit të njoftimit.
     *
     * @return mesazhi
     */
    public String getMessage() {
        return message;
    }

    /**
     * Vendos përmbajtjen e mesazhit të njoftimit.
     *
     * @param message mesazhi i ri
     */
    public void setMessage(String message) {
        this.message = message;
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
     * @param createdAt data/orë e re
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
