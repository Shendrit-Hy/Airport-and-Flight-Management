package com.mbi_re.airport_management.model;

import jakarta.persistence.*;

/**
 * Entiteti {@code ParkingPrice} përfaqëson çmimin e llogaritur për një periudhë parkimi
 * në një aeroport të caktuar. Ai përfshin orarin e hyrjes dhe daljes, çmimin total
 * dhe identifikuesin e tenantit për sistemin multi-tenant.
 */
@Entity
@Table(name = "parking_prices")
public class ParkingPrice {

    /** ID unike e rreshtit të çmimit të parkimit */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Ora e hyrjes në parking (0-23) */
    @Column(name = "entry_hour")
    private int entryHour;

    /** Minutat e hyrjes në parking (0-59) */
    @Column(name = "entry_minute")
    private int entryMinute;

    /** Ora e daljes nga parkingu (0-23) */
    @Column(name = "exit_hour")
    private int exitHour;

    /** Minutat e daljes nga parkingu (0-59) */
    @Column(name = "exit_minute")
    private int exitMinute;

    /** Çmimi total për kohëzgjatjen e parkimit */
    private double price;

    /** ID e tenantit për të përkrahur multi-tenancy */
    @Column(name = "tenant_id")
    private String tenantId;

    /**
     * Konstruktori i zbrazët i nevojshëm për JPA.
     */
    public ParkingPrice() {
    }

    /**
     * Konstruktori me parametra për të inicializuar një objekt {@code ParkingPrice}.
     *
     * @param id ID e entitetit
     * @param entryHour ora e hyrjes
     * @param entryMinute minutat e hyrjes
     * @param exitHour ora e daljes
     * @param exitMinute minutat e daljes
     * @param price çmimi total
     * @param tenantId tenant ID i lidhur
     */
    public ParkingPrice(Long id, int entryHour, int entryMinute, int exitHour, int exitMinute, double price, String tenantId) {
        this.id = id;
        this.entryHour = entryHour;
        this.entryMinute = entryMinute;
        this.exitHour = exitHour;
        this.exitMinute = exitMinute;
        this.price = price;
        this.tenantId = tenantId;
    }

    // Getters & Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getEntryHour() {
        return entryHour;
    }

    public void setEntryHour(int entryHour) {
        this.entryHour = entryHour;
    }

    public int getEntryMinute() {
        return entryMinute;
    }

    public void setEntryMinute(int entryMinute) {
        this.entryMinute = entryMinute;
    }

    public int getExitHour() {
        return exitHour;
    }

    public void setExitHour(int exitHour) {
        this.exitHour = exitHour;
    }

    public int getExitMinute() {
        return exitMinute;
    }

    public void setExitMinute(int exitMinute) {
        this.exitMinute = exitMinute;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
