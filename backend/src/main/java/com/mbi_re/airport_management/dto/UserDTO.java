package com.mbi_re.airport_management.dto;

import lombok.*;

/**
 * DTO (Data Transfer Object) për përfaqësimin e një përdoruesi në sistem.
 * Përdoret për regjistrim, autentikim dhe menaxhim të të dhënave të përdoruesve.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    /** ID unike e përdoruesit */
    private Long id;

    /** Emri dhe mbiemri i plotë i përdoruesit */
    private String fullname;

    /** Emri i përdoruesit për kyçje (login) */
    private String username;

    /** Email-i i përdoruesit */
    private String email;

    /** Fjalëkalimi i përdoruesit */
    private String password;

    /** Vendi i përdoruesit (p.sh., Albania, Germany) */
    private String country;

    /** ID e tenantit për mbështetje multi-tenant */
    private String tenantId;

    /** Roli i përdoruesit në sistem (p.sh., USER, ADMIN) */
    private String role;

    /**
     * Merr ID-në e përdoruesit.
     *
     * @return ID si {@code Long}
     */
    public Long getId() {
        return id;
    }

    /**
     * Vendos ID-në e përdoruesit.
     *
     * @param id ID e re
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Merr emrin e plotë të përdoruesit.
     *
     * @return emri i plotë
     */
    public String getFullname() {
        return fullname;
    }

    /**
     * Vendos emrin e plotë të përdoruesit.
     *
     * @param fullname emri i ri i plotë
     */
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    /**
     * Merr vendin e përdoruesit.
     *
     * @return vendi si tekst
     */
    public String getCountry() {
        return country;
    }

    /**
     * Vendos vendin e përdoruesit.
     *
     * @param country vendi i ri
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Merr emrin e përdoruesit për login.
     *
     * @return username si tekst
     */
    public String getUsername() {
        return username;
    }

    /**
     * Vendos emrin e përdoruesit për login.
     *
     * @param username emri i ri i përdoruesit
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Merr email-in e përdoruesit.
     *
     * @return email-i
     */
    public String getEmail() {
        return email;
    }

    /**
     * Vendos email-in e përdoruesit.
     *
     * @param email email-i i ri
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Merr fjalëkalimin e përdoruesit.
     *
     * @return fjalëkalimi si tekst
     */
    public String getPassword() {
        return password;
    }

    /**
     * Vendos fjalëkalimin e përdoruesit.
     *
     * @param password fjalëkalimi i ri
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Merr tenantId-in e përdoruesit.
     *
     * @return tenantId si tekst
     */
    public String getTenantId() {
        return tenantId;
    }

    /**
     * Vendos tenantId-in e përdoruesit.
     *
     * @param tenantId tenantId i ri
     */
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    /**
     * Merr rolin e përdoruesit.
     *
     * @return roli si tekst
     */
    public String getRole() {
        return role;
    }

    /**
     * Vendos rolin e përdoruesit.
     *
     * @param role roli i ri
     */
    public void setRole(String role) {
        this.role = role;
    }
}
