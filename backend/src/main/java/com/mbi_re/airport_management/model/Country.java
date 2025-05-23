package com.mbi_re.airport_management.model;

import jakarta.persistence.*;

/**
 * Entiteti {@code Country} përfaqëson një shtet në sistemin e menaxhimit të aeroporteve.
 * Ky entitet lidhet me qytetet dhe aeroportet dhe përdoret për kategorizimin gjeografik.
 */
@Entity
@Table(name = "countries")
public class Country {

    /** ID unike e shtetit, e gjeneruar automatikisht */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Emri i shtetit (p.sh., "Kosova", "Germany") */
    @Column(nullable = false, unique = true)
    private String name;

    /** Kodi unik i shtetit (p.sh., "KS", "DE") */
    @Column(nullable = false, unique = true)
    private String code;

    /** ID e tenantit për mbështetjen e sistemit multi-tenant */
    @Column(name = "tenant_id", nullable = false)
    private String tenantId;

    /**
     * Konstruktori i zbrazët për JPA dhe deserializim.
     */
    public Country() {
    }

    /**
     * Konstruktori me parametra për të inicializuar një objekt {@code Country}.
     *
     * @param id ID e shtetit
     * @param name emri i shtetit
     */
    public Country(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Merr ID-në e shtetit.
     *
     * @return ID si {@code Long}
     */
    public Long getId() {
        return id;
    }

    /**
     * Vendos ID-në e shtetit.
     *
     * @param id ID e re
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Merr emrin e shtetit.
     *
     * @return emri si tekst
     */
    public String getName() {
        return name;
    }

    /**
     * Vendos emrin e shtetit.
     *
     * @param name emri i ri
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Merr kodin e shtetit.
     *
     * @return kodi si tekst (p.sh., "AL", "US")
     */
    public String getCode() {
        return code;
    }

    /**
     * Vendos kodin e shtetit.
     *
     * @param code kodi i ri
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Merr tenantId-in që lidhet me këtë shtet në sistemin multi-tenant.
     *
     * @return tenantId si tekst
     */
    public String getTenantId() {
        return tenantId;
    }

    /**
     * Vendos tenantId-in për këtë shtet.
     *
     * @param tenantId tenantId i ri
     */
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
