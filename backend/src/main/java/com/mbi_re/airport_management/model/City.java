package com.mbi_re.airport_management.model;

import jakarta.persistence.*;

/**
 * Entiteti {@code City} përfaqëson një qytet që lidhet me një shtet të caktuar.
 * Kjo klasë është e lidhur me aeroportet dhe përdoret për organizimin gjeografik të tyre.
 */
@Entity
@Table(name = "cities")
public class City {

    /** ID unike e qytetit, e gjeneruar automatikisht nga databaza */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Emri i qytetit (p.sh., Prishtina, Tirana) */
    @Column(nullable = false)
    private String name;

    /** Shteti që i përket ky qytet */
    @ManyToOne
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;

    /** ID e tenantit që posedon të dhënat në sistemin multi-tenant */
    @Column(name = "tenant_id", nullable = false)
    private String tenantId;

    /**
     * Konstruktori i zbrazët i nevojshëm për JPA.
     */
    public City() {}

    /**
     * Konstruktori me parametra për inicializim të qytetit.
     *
     * @param id ID e qytetit
     * @param name emri i qytetit
     * @param country objekti i shtetit përkatës
     */
    public City(Long id, String name, Country country) {
        this.id = id;
        this.name = name;
        this.country = country;
    }

    /**
     * Merr ID-në e qytetit.
     *
     * @return ID si {@code Long}
     */
    public Long getId() {
        return id;
    }

    /**
     * Vendos ID-në e qytetit.
     *
     * @param id ID e re
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Merr emrin e qytetit.
     *
     * @return emri i qytetit
     */
    public String getName() {
        return name;
    }

    /**
     * Vendos emrin e qytetit.
     *
     * @param name emri i ri
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Merr shtetin që lidhet me këtë qytet.
     *
     * @return objekti {@code Country}
     */
    public Country getCountry() {
        return country;
    }

    /**
     * Vendos shtetin që lidhet me këtë qytet.
     *
     * @param country objekti {@code Country}
     */
    public void setCountry(Country country) {
        this.country = country;
    }

    /**
     * Merr tenantId-in që identifikon organizatën në sistem.
     *
     * @return tenantId si tekst
     */
    public String getTenantId() {
        return tenantId;
    }

    /**
     * Vendos tenantId-in për këtë qytet.
     *
     * @param tenantId ID e re e tenantit
     */
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
