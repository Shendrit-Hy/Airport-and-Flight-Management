package com.mbi_re.airport_management.model;

import jakarta.persistence.*;

/**
 * Entiteti {@code Airline} përfaqëson një kompani ajrore në bazën e të dhënave.
 * Ky model është i lidhur me tabelën {@code airlines} dhe përfshin të dhëna të tilla si emri i linjës ajrore dhe tenantId për mbështetje multi-tenancy.
 */
@Entity
@Table(name = "airlines")
public class Airline {

    /** ID unike e linjës ajrore, e gjeneruar automatikisht */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Emri i linjës ajrore, duhet të jetë unik dhe jo null */
    @Column(nullable = false, unique = true)
    private String name;

    /** ID e tenantit që e posedon këtë linjë ajrore në një sistem multi-tenant */
    @Column(name = "tenant_id", nullable = false)
    private String tenantId;

    /**
     * Konstruktori i zbrazët i nevojshëm nga JPA.
     */
    public Airline() {}

    /**
     * Konstruktori me parametra për inicializim të objektit {@code Airline}.
     *
     * @param id ID e linjës ajrore
     * @param name emri i linjës ajrore
     */
    public Airline(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Merr ID-në e linjës ajrore.
     *
     * @return ID si {@code Long}
     */
    public Long getId() {
        return id;
    }

    /**
     * Vendos ID-në e linjës ajrore.
     *
     * @param id ID e re
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Merr emrin e linjës ajrore.
     *
     * @return emri si {@code String}
     */
    public String getName() {
        return name;
    }

    /**
     * Vendos emrin e linjës ajrore.
     *
     * @param name emri i ri
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Merr tenantId-in që i përket kësaj linje ajrore.
     *
     * @return tenantId si {@code String}
     */
    public String getTenantId() {
        return tenantId;
    }

    /**
     * Vendos tenantId-in që i përket kësaj linje ajrore.
     *
     * @param tenantId tenantId i ri
     */
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
