package com.mbi_re.airport_management.model;

import jakarta.persistence.*;

/**
 * Entiteti {@code Passenger} përfaqëson një pasagjer në sistemin e menaxhimit të aeroporteve dhe fluturimeve.
 * Për çdo pasagjer ruhen të dhëna personale, si dhe lidhen me një tenant të caktuar në një sistem multi-tenant.
 */
@Entity
@Table(name = "passengers")
public class Passenger {

    /** ID unike e pasagjerit, e gjeneruar automatikisht nga databaza */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Emri i plotë i pasagjerit */
    private String fullName;

    /** Email-i i pasagjerit për komunikim ose identifikim */
    private String email;

    /** Numri i telefonit të pasagjerit */
    private String phone;

    /** Mosha e pasagjerit */
    private Long age;

    /** ID e tenantit për të mbështetur sistemin multi-tenant */
    @Column(name = "tenant_id")
    private String tenantId;

    /**
     * Konstruktori i zbrazët i nevojshëm për JPA dhe deserializim.
     */
    public Passenger() {}

    /**
     * Konstruktori me parametra për të inicializuar një objekt {@code Passenger}.
     *
     * @param id ID e pasagjerit
     * @param fullName emri i plotë
     * @param email email-i i pasagjerit
     * @param phone numri i telefonit
     * @param age mosha
     * @param tenantId ID e tenantit
     */
    public Passenger(Long id, String fullName, String email, String phone, Long age, String tenantId) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.age = age;
        this.tenantId = tenantId;
    }

    // Getters dhe Setters

    /**
     * Merr ID-në e pasagjerit.
     *
     * @return ID si {@code Long}
     */
    public Long getId() {
        return id;
    }

    /**
     * Vendos ID-në e pasagjerit.
     *
     * @param id ID e re
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Merr emrin e plotë të pasagjerit.
     *
     * @return emri i plotë si tekst
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Vendos emrin e plotë të pasagjerit.
     *
     * @param fullName emri i ri
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * Merr email-in e pasagjerit.
     *
     * @return email si tekst
     */
    public String getEmail() {
        return email;
    }

    /**
     * Vendos email-in e pasagjerit.
     *
     * @param email email i ri
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Merr numrin e telefonit të pasagjerit.
     *
     * @return telefoni si tekst
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Vendos numrin e telefonit të pasagjerit.
     *
     * @param phone telefoni i ri
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Merr moshën e pasagjerit.
     *
     * @return mosha si {@code Long}
     */
    public Long getAge() {
        return age;
    }

    /**
     * Vendos moshën e pasagjerit.
     *
     * @param age mosha e re
     */
    public void setAge(Long age) {
        this.age = age;
    }

    /**
     * Merr tenantId-in e lidhur me këtë pasagjer.
     *
     * @return tenantId si tekst
     */
    public String getTenantId() {
        return tenantId;
    }

    /**
     * Vendos tenantId-in për këtë pasagjer.
     *
     * @param tenantId tenantId i ri
     */
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
