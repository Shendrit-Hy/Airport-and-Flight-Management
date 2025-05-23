package com.mbi_re.airport_management.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entiteti {@code Policy} përfaqëson një politikë specifike në sistemin e menaxhimit të aeroporteve.
 * Kjo mund të përfshijë politika udhëtimi, anulimi, ose rregulla të tjera që kanë të bëjnë me shërbimet.
 * Çdo politikë është e lidhur me një tenant të caktuar dhe ruan kohën e krijimit.
 */
@Entity
public class Policy {

    /** ID unike e politikës, e gjeneruar automatikisht */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Titulli i politikës (p.sh., "Travel Policy") */
    private String title;

    /** Përmbajtja e plotë e politikës, deri në 5000 karaktere */
    @Column(length = 5000)
    private String content;

    /** Tipi i politikës (p.sh., "Travel", "Cancellation", etj.) */
    private String type;

    /** Data dhe ora kur është krijuar politika */
    private LocalDateTime createdAt;

    /** ID e tenantit për mbështetje në arkitekturë multi-tenant */
    @Column(name = "tenant_id", nullable = false)
    private String tenantId;

    /**
     * Merr ID-në e politikës.
     *
     * @return ID si {@code Long}
     */
    public Long getId() {
        return id;
    }

    /**
     * Vendos ID-në e politikës.
     *
     * @param id ID e re
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Merr titullin e politikës.
     *
     * @return titulli si tekst
     */
    public String getTitle() {
        return title;
    }

    /**
     * Vendos titullin e politikës.
     *
     * @param title titulli i ri
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Merr përmbajtjen e politikës.
     *
     * @return përmbajtja si tekst
     */
    public String getContent() {
        return content;
    }

    /**
     * Vendos përmbajtjen e politikës.
     *
     * @param content përmbajtja e re
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Merr tipin e politikës.
     *
     * @return tipi si tekst (p.sh., "Travel")
     */
    public String getType() {
        return type;
    }

    /**
     * Vendos tipin e politikës.
     *
     * @param type tipi i ri
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Merr kohën e krijimit të politikës.
     *
     * @return {@code LocalDateTime} e krijimit
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Vendos kohën e krijimit të politikës.
     *
     * @param createdAt data/orari i krijimit
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Merr tenantId-in e organizatës që zotëron këtë politikë.
     *
     * @return tenantId si tekst
     */
    public String getTenantId() {
        return tenantId;
    }

    /**
     * Vendos tenantId-in e organizatës që zotëron këtë politikë.
     *
     * @param tenantId ID i ri
     */
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    /**
     * Inicializon automatikisht fushën {@code createdAt} me kohën aktuale
     * përpara se objekti të ruhet në bazën e të dhënave.
     */
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
