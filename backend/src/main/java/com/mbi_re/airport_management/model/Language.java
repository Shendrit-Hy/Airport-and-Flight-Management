package com.mbi_re.airport_management.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entiteti {@code Language} përfaqëson një gjuhë të mbështetur në sistemin e menaxhimit të aeroporteve.
 * Ky entitet përdoret për të përkrahur shumëgjuhësinë dhe është i lidhur me një tenant specifik në arkitekturën multi-tenant.
 */
@Entity
@Table(name = "languages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Language {

    /** ID unike e gjuhës, e gjeneruar automatikisht */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Kodi i gjuhës (p.sh., "en" për anglisht, "sq" për shqip) */
    private String code;

    /** Emri i plotë i gjuhës (p.sh., "English", "Shqip") */
    private String name;

    /** ID e tenantit për mbështetje multi-tenancy */
    @Column(name = "tenant_id", nullable = false)
    private String tenantId;

    /**
     * Merr ID-në e gjuhës.
     *
     * @return ID si {@code Long}
     */
    public Long getId() {
        return id;
    }

    /**
     * Vendos ID-në e gjuhës.
     *
     * @param id ID e re
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Merr kodin e gjuhës.
     *
     * @return kodi i gjuhës si tekst (p.sh., "en")
     */
    public String getCode() {
        return code;
    }

    /**
     * Vendos kodin e gjuhës.
     *
     * @param code kodi i ri i gjuhës
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Merr emrin e gjuhës.
     *
     * @return emri i gjuhës si tekst (p.sh., "English")
     */
    public String getName() {
        return name;
    }

    /**
     * Vendos emrin e gjuhës.
     *
     * @param name emri i ri i gjuhës
     */
    public void setName(String name) {
        this.name = name;
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
