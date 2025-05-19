package com.mbi_re.airport_management.model;

import jakarta.persistence.*;

@Entity
@Table(name = "parking_prices")
public class ParkingPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "entry_hour")
    private int entryHour;

    @Column(name = "entry_minute")
    private int entryMinute;

    @Column(name = "exit_hour")
    private int exitHour;

    @Column(name = "exit_minute")
    private int exitMinute;

    private double price;

    @Column(name = "tenant_id")
    private String tenantId;

    public ParkingPrice() {
    }

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
