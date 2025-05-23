package com.mbi_re.airport_management.dto;

/**
 * DTO (Data Transfer Object) për kalkulimin e çmimit të parkingut në aeroport.
 * Përdoret për të dërguar të dhënat e hyrjes dhe daljes për llogaritjen e tarifës së parkingut.
 */
public class ParkingPriceDTO {

    /** Ora e hyrjes në parking (0-23) */
    private int entryHour;

    /** Minutat e hyrjes në parking (0-59) */
    private int entryMinute;

    /** Ora e daljes nga parkingu (0-23) */
    private int exitHour;

    /** Minutat e daljes nga parkingu (0-59) */
    private int exitMinute;

    /** ID e tenantit për të identifikuar organizatën në sistemin multi-tenant */
    private String tenantId;

    /** Konstruktori i zbrazët për deserializim */
    public ParkingPriceDTO() {}

    /**
     * Konstruktori me parametra për të krijuar një objekt ParkingPriceDTO.
     *
     * @param entryHour ora e hyrjes
     * @param entryMinute minutat e hyrjes
     * @param exitHour ora e daljes
     * @param exitMinute minutat e daljes
     * @param tenantId ID e tenantit
     */
    public ParkingPriceDTO(int entryHour, int entryMinute, int exitHour, int exitMinute, String tenantId) {
        this.entryHour = entryHour;
        this.entryMinute = entryMinute;
        this.exitHour = exitHour;
        this.exitMinute = exitMinute;
        this.tenantId = tenantId;
    }

    /**
     * Merr orën e hyrjes në parking.
     *
     * @return ora e hyrjes
     */
    public int getEntryHour() {
        return entryHour;
    }

    /**
     * Vendos orën e hyrjes në parking.
     *
     * @param entryHour ora e re e hyrjes
     */
    public void setEntryHour(int entryHour) {
        this.entryHour = entryHour;
    }

    /**
     * Merr minutat e hyrjes në parking.
     *
     * @return minutat e hyrjes
     */
    public int getEntryMinute() {
        return entryMinute;
    }

    /**
     * Vendos minutat e hyrjes në parking.
     *
     * @param entryMinute minutat e reja të hyrjes
     */
    public void setEntryMinute(int entryMinute) {
        this.entryMinute = entryMinute;
    }

    /**
     * Merr orën e daljes nga parkingu.
     *
     * @return ora e daljes
     */
    public int getExitHour() {
        return exitHour;
    }

    /**
     * Vendos orën e daljes nga parkingu.
     *
     * @param exitHour ora e re e daljes
     */
    public void setExitHour(int exitHour) {
        this.exitHour = exitHour;
    }

    /**
     * Merr minutat e daljes nga parkingu.
     *
     * @return minutat e daljes
     */
    public int getExitMinute() {
        return exitMinute;
    }

    /**
     * Vendos minutat e daljes nga parkingu.
     *
     * @param exitMinute minutat e reja të daljes
     */
    public void setExitMinute(int exitMinute) {
        this.exitMinute = exitMinute;
    }

    /**
     * Merr ID-në e tenantit.
     *
     * @return tenantId si tekst
     */
    public String getTenantId() {
        return tenantId;
    }

    /**
     * Vendos ID-në e tenantit.
     *
     * @param tenantId ID e re e tenantit
     */
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
