package com.mbi_re.airport_management.model;

import jakarta.persistence.*;

/**
 * Entiteti {@code Terminal} përfaqëson një terminal fizik brenda një aeroporti specifik.
 * Ai përfshin emrin e terminalit, lidhjen me aeroportin dhe tenant ID për mbështetje në sistem multi-tenant.
 */
@Entity
@Table(name = "terminals")
public class Terminal {

    /** ID unike e terminalit, e gjeneruar automatikisht nga databaza */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Emri i terminalit (p.sh., "Terminal A", "Terminal B") */
    @Column(nullable = false)
    private String name;

    /** Aeroporti ku ndodhet ky terminal */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "airport_id", nullable = false)
    private Airport airport;

    /** ID e tenantit për mbështetje në arkitekturë multi-tenant */
    @Column(nullable = false)
    private String tenantId;

    /**
     * Konstruktori i zbrazët i nevojshëm për JPA.
     */
    public Terminal() {}

    /**
     * Konstruktori me parametra për të inicializuar një objekt {@code Terminal}.
     *
     * @param name emri i terminalit
     * @param airport aeroporti ku ndodhet
     * @param tenantId tenant ID për organizatën përkatëse
     */
    public Terminal(String name, Airport airport, String tenantId) {
        this.name = name;
        this.airport = airport;
        this.tenantId = tenantId;
    }

    // Getters dhe Setters

    /**
     * Merr ID-në e terminalit.
     *
     * @return ID si {@code Long}
     */
    public Long getId() {
        return id;
    }

    /**
     * Vendos ID-në e terminalit.
     *
     * @param id ID e re
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Merr emrin e terminalit.
     *
     * @return emri si tekst
     */
    public String getName() {
        return name;
    }

    /**
     * Vendos emrin e terminalit.
     *
     * @param name emri i ri
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Merr aeroportin ku ndodhet ky terminal.
     *
     * @return objekti {@code Airport}
     */
    public Airport getAirport() {
        return airport;
    }

    /**
     * Vendos aeroportin ku ndodhet ky terminal.
     *
     * @param airport aeroporti i ri
     */
    public void setAirport(Airport airport) {
        this.airport = airport;
    }

    /**
     * Merr tenantId-in që përfaqëson organizatën për këtë terminal.
     *
     * @return tenantId si tekst
     */
    public String getTenantId() {
        return tenantId;
    }

    /**
     * Vendos tenantId-in për këtë terminal.
     *
     * @param tenantId tenant ID i ri
     */
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
