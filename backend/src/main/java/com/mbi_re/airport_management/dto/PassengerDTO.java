package com.mbi_re.airport_management.dto;

/**
 * DTO (Data Transfer Object) për përfaqësimin e një pasagjeri në sistemin e menaxhimit të fluturimeve.
 * Përdoret për të transferuar të dhëna të pasagjerëve midis shtresave të aplikacionit.
 */
public class PassengerDTO {

    /** ID unike e pasagjerit */
    private Long id;

    /** Emri dhe mbiemri i pasagjerit */
    private String fullName;

    /** Adresa e emailit të pasagjerit */
    private String email;

    /** Numri i telefonit të pasagjerit */
    private String phone;

    /** Mosha e pasagjerit */
    private Long age;

    /** ID e tenantit për mbështetjen e multi-tenancy në sistem */
    private String tenantId;

    /** Konstruktori i zbrazët për deserializim */
    public PassengerDTO() {}

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
     * @param id ID e re e pasagjerit
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Merr tenantId-in e pasagjerit.
     *
     * @return tenantId si tekst
     */
    public String getTenantId() {
        return tenantId;
    }

    /**
     * Vendos tenantId-in për pasagjerin.
     *
     * @param tenantId tenantId i ri
     */
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    /**
     * Merr emrin e plotë të pasagjerit.
     *
     * @return emri i plotë
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Vendos emrin e plotë të pasagjerit.
     *
     * @param fullName emri i ri i plotë
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * Merr adresën e emailit të pasagjerit.
     *
     * @return emaili
     */
    public String getEmail() {
        return email;
    }

    /**
     * Vendos adresën e emailit të pasagjerit.
     *
     * @param email emaili i ri
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Merr numrin e telefonit të pasagjerit.
     *
     * @return numri i telefonit
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Vendos numrin e telefonit të pasagjerit.
     *
     * @param phone numri i ri i telefonit
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
}
