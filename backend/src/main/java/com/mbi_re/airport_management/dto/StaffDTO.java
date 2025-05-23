package com.mbi_re.airport_management.dto;

import java.time.LocalTime;

/**
 * DTO (Data Transfer Object) për përfaqësimin e një anëtari të stafit në aeroport.
 * Përdoret për transferimin e të dhënave të stafit midis shtresave të aplikacionit.
 */
public class StaffDTO {

    /** ID unike e anëtarit të stafit */
    private Long id;

    /** Emri i anëtarit të stafit */
    private String name;

    /** Roli i anëtarit të stafit (p.sh., Teknik, Siguri, Asistent) */
    private String role;

    /** Email-i i anëtarit të stafit */
    private String email;

    /** Ora e fillimit të ndërrimit të punës */
    private LocalTime shiftStart;

    /** Ora e përfundimit të ndërrimit të punës */
    private LocalTime shiftEnd;

    /** ID e tenantit për të mbështetur multi-tenancy */
    private String tenantId;

    /**
     * Merr orën e fillimit të ndërrimit.
     *
     * @return ora e fillimit
     */
    public LocalTime getShiftStart() {
        return shiftStart;
    }

    /**
     * Vendos orën e fillimit të ndërrimit.
     *
     * @param shiftStart ora e re e fillimit
     */
    public void setShiftStart(LocalTime shiftStart) {
        this.shiftStart = shiftStart;
    }

    /**
     * Merr orën e përfundimit të ndërrimit.
     *
     * @return ora e përfundimit
     */
    public LocalTime getShiftEnd() {
        return shiftEnd;
    }

    /**
     * Vendos orën e përfundimit të ndërrimit.
     *
     * @param shiftEnd ora e re e përfundimit
     */
    public void setShiftEnd(LocalTime shiftEnd) {
        this.shiftEnd = shiftEnd;
    }

    /**
     * Merr tenantId-in e anëtarit të stafit.
     *
     * @return tenantId si tekst
     */
    public String getTenantId() {
        return tenantId;
    }

    /**
     * Vendos tenantId-in e anëtarit të stafit.
     *
     * @param tenantId tenantId i ri
     */
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

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
     * @return email-i
     */
    public String getEmail() {
        return email;
    }

    /**
     * Vendos email-in e anëtarit të stafit.
     *
     * @param email email-i i ri
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
