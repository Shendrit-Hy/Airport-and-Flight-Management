package com.mbi_re.airport_management.model;

import jakarta.persistence.*;
import java.time.LocalTime;

/**
 * Entiteti {@code Staff} përfaqëson një anëtar të stafit në një aeroport të caktuar.
 * Përfshin të dhëna të përgjithshme si emri, roli, email-i dhe orari i punës, si dhe lidhet me një tenant specifik për mbështetje multi-tenant.
 */
@Entity
@Table(name = "staff")
public class Staff {

    /** ID unike e anëtarit të stafit, e gjeneruar automatikisht */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Emri i plotë i anëtarit të stafit */
    private String name;

    /** Roli i anëtarit të stafit (p.sh., "Security", "Technician", "Manager") */
    private String role;

    /** Email-i i anëtarit të stafit */
    private String email;

    /** Ora kur fillon turni i punës së stafit */
    private LocalTime shiftStart;

    /** Ora kur përfundon turni i punës së stafit */
    private LocalTime shiftEnd;

    /** ID e tenantit për të mbështetur multi-tenancy */
    @Column(name = "tenant_id", nullable = false)
    private String tenantId;

    // Getters dhe Setters

    /**
     * Merr ID-në e anëtarit të stafit.
     *
     * @return ID si {@code Long}
     */
    public Long getId() {
        return id;
    }

    /**
     * Vendos ID-në e anëtarit të stafit.
     *
     * @param id ID e re
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Merr emrin e anëtarit të stafit.
     *
     * @return emri si tekst
     */
    public String getName() {
        return name;
    }

    /**
     * Vendos emrin e anëtarit të stafit.
     *
     * @param name emri i ri
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Merr rolin e anëtarit të stafit.
     *
     * @return roli si tekst
     */
    public String getRole() {
        return role;
    }

    /**
     * Vendos rolin e anëtarit të stafit.
     *
     * @param role roli i ri
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Merr email-in e anëtarit të stafit.
     *
     * @return email si tekst
     */
    public String getEmail() {
        return email;
    }

    /**
     * Vendos email-in e anëtarit të stafit.
     *
     * @param email email i ri
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Merr kohën kur fillon turni i punës së stafit.
     *
     * @return ora e fillimit
     */
    public LocalTime getShiftStart() {
        return shiftStart;
    }

    /**
     * Vendos kohën e fillimit të turnit të stafit.
     *
     * @param shiftStart ora e re e fillimit
     */
    public void setShiftStart(LocalTime shiftStart) {
        this.shiftStart = shiftStart;
    }

    /**
     * Merr kohën kur përfundon turni i punës së stafit.
     *
     * @return ora e përfundimit
     */
    public LocalTime getShiftEnd() {
        return shiftEnd;
    }

    /**
     * Vendos kohën e përfundimit të turnit të stafit.
     *
     * @param shiftEnd ora e re e përfundimit
     */
    public void setShiftEnd(LocalTime shiftEnd) {
        this.shiftEnd = shiftEnd;
    }

    /**
     * Merr tenantId-in që lidhet me këtë anëtar stafi.
     *
     * @return tenantId si tekst
     */
    public String getTenantId() {
        return tenantId;
    }

    /**
     * Vendos tenantId-in për këtë anëtar stafi.
     *
     * @param tenantId tenant ID i ri
     */
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
