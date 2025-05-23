package com.mbi_re.airport_management.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

/**
 * Entiteti {@code CurrencyRate} përfaqëson kursin e këmbimit të një monedhe kundrejt dollarit amerikan (USD).
 * Përdoret për qëllime konvertimi në transaksione ose shfaqje çmimesh në monedha të ndryshme.
 */
@Entity
public class CurrencyRate {

    /** Kodi i monedhës (p.sh., "USD", "EUR", "GBP") – ky është edhe ID-ja primare */
    @Id
    private String code;

    /** Kursi i këmbimit të kësaj monedhe kundrejt USD (p.sh., 1.0 për USD, 0.93 për EUR) */
    private double rateToUSD;

    /** ID e tenantit për të mbështetur multi-tenancy në sistemin e valutave */
    @Column(name = "tenant_id", nullable = false)
    private String tenantId;

    /**
     * Merr tenantId-in e lidhur me këtë valutë.
     *
     * @return tenantId si tekst
     */
    public String getTenantId() {
        return tenantId;
    }

    /**
     * Vendos tenantId-in për këtë valutë.
     *
     * @param tenantId ID e re e tenantit
     */
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    /**
     * Merr kodin e monedhës.
     *
     * @return kodi i monedhës (p.sh., "EUR")
     */
    public String getCode() {
        return code;
    }

    /**
     * Vendos kodin e monedhës.
     *
     * @param code kodi i ri i monedhës
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Merr kursin e këmbimit të monedhës kundrejt USD.
     *
     * @return vlera numerike e kursit (p.sh., 0.93)
     */
    public double getRateToUSD() {
        return rateToUSD;
    }

    /**
     * Vendos kursin e këmbimit të monedhës kundrejt USD.
     *
     * @param rateToUSD kursi i ri
     */
    public void setRateToUSD(double rateToUSD) {
        this.rateToUSD = rateToUSD;
    }
}
