package com.mbi_re.airport_management.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Entiteti {@code User} përfaqëson një përdorues të sistemit të menaxhimit të aeroporteve.
 * Ky përdorues mund të jetë administrator, agjent, apo përdorues i thjeshtë.
 * Përdoruesi ka lidhje me një shtet, rolin e tij dhe një tenant specifik për multi-tenancy.
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    /** ID unike e përdoruesit, e gjeneruar automatikisht nga databaza */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Emri i përdoruesit, duhet të jetë unik dhe jo null */
    @Column(unique = true, nullable = false)
    private String username;

    /** Email-i i përdoruesit, gjithashtu unik */
    @Column(unique = true)
    private String email;

    /** Emri i plotë i përdoruesit */
    @Column(nullable = false)
    private String fullName;

    /** Vendbanimi (shteti) i përdoruesit */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;

    /** Fjalëkalimi i përdoruesit, i enkriptuar në databazë */
    @Column(nullable = false)
    private String password;

    /** Tenant ID për të mbështetur izolimin e të dhënave në arkitekturën multi-tenant */
    @Column(name = "tenant_id", nullable = false)
    private String tenantId;

    /** Roli i përdoruesit (p.sh., ADMIN, USER) */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    // Getters & Setters

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
     * Merr emrin e përdoruesit.
     *
     * @return emri i përdoruesit
     */
    public String getUsername() {
        return username;
    }

    /**
     * Vendos emrin e përdoruesit.
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
     * @param email email i ri
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Merr emrin e plotë të përdoruesit.
     *
     * @return emri i plotë
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Vendos emrin e plotë të përdoruesit.
     *
     * @param fullName emri i ri
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * Merr shtetin e përdoruesit.
     *
     * @return objekti {@code Country}
     */
    public Country getCountry() {
        return country;
    }

    /**
     * Vendos shtetin e përdoruesit.
     *
     * @param country objekti {@code Country}
     */
    public void setCountry(Country country) {
        this.country = country;
    }

    /**
     * Merr fjalëkalimin e përdoruesit.
     *
     * @return fjalëkalimi i enkriptuar
     */
    public String getPassword() {
        return password;
    }

    /**
     * Vendos fjalëkalimin e përdoruesit.
     *
     * @param password fjalëkalimi i ri (duhet të jetë i enkriptuar)
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Merr tenantId-in për identifikimin e organizatës së përdoruesit.
     *
     * @return tenant ID si tekst
     */
    public String getTenantId() {
        return tenantId;
    }

    /**
     * Vendos tenantId-in e përdoruesit.
     *
     * @param tenantId ID e tenantit
     */
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    /**
     * Merr rolin e përdoruesit.
     *
     * @return roli si {@code Role}
     */
    public Role getRole() {
        return role;
    }

    /**
     * Vendos rolin e përdoruesit.
     *
     * @param role roli i ri
     */
    public void setRole(Role role) {
        this.role = role;
    }
}
