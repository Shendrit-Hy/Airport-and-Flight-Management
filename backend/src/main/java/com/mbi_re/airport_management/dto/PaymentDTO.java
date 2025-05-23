package com.mbi_re.airport_management.dto;

import lombok.Data;
import java.math.BigDecimal;

/**
 * DTO (Data Transfer Object) për përfaqësimin e një pagese.
 * Përdoret për transferimin e të dhënave të pagesave midis shtresave të aplikacionit.
 */
@Data
public class PaymentDTO {

    /** Metoda e pagesës (p.sh., Credit Card, PayPal, Cash) */
    private String method;

    /** Shuma totale e pagesës */
    private BigDecimal amount;

    /** Statusi i pagesës (p.sh., SUCCESS, PENDING, FAILED) */
    private String status;

    /** Referencë unike për pagesën (mund të jetë ID nga sistemi i pagesave) */
    private String reference;

    /** ID e tenantit për të mbështetur multi-tenancy në sistem */
    private String tenantId;

    /**
     * Merr metodën e pagesës.
     *
     * @return metoda e pagesës
     */
    public String getMethod() {
        return method;
    }

    /**
     * Vendos metodën e pagesës.
     *
     * @param method metoda e re e pagesës
     */
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * Merr shumën e pagesës.
     *
     * @return shuma si {@code BigDecimal}
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * Vendos shumën e pagesës.
     *
     * @param amount shuma e re
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * Merr statusin e pagesës.
     *
     * @return statusi si tekst
     */
    public String getStatus() {
        return status;
    }

    /**
     * Vendos statusin e pagesës.
     *
     * @param status statusi i ri i pagesës
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Merr referencën e pagesës.
     *
     * @return referenca e pagesës
     */
    public String getReference() {
        return reference;
    }

    /**
     * Vendos referencën e pagesës.
     *
     * @param reference referenca e re
     */
    public void setReference(String reference) {
        this.reference = reference;
    }

    /**
     * Merr tenantId-in për pagesën.
     *
     * @return tenantId si tekst
     */
    public String getTenantId() {
        return tenantId;
    }

    /**
     * Vendos tenantId-in për pagesën.
     *
     * @param tenantId tenantId i ri
     */
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
