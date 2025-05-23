package com.mbi_re.airport_management.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entiteti {@code Payment} përfaqëson një pagesë të realizuar në sistemin e menaxhimit të fluturimeve.
 * Ai përmban informacion për metodën e pagesës, shumën, kohën e kryerjes, statusin dhe referencën e transaksionit.
 */
@Entity
@Table(name = "payments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

    /** ID unike e pagesës, e gjeneruar automatikisht nga databaza */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Metoda e pagesës (p.sh., "CREDIT_CARD", "PAYPAL") */
    private String method;

    /** Shuma e pagesës në format decimal për saktësi monetare */
    private BigDecimal amount;

    /** Koha kur është kryer pagesa */
    private LocalDateTime paymentTime;

    /** Statusi aktual i pagesës (p.sh., "PAID", "FAILED", "PENDING") */
    private String status;

    /** Referenca e transaksionit ose kodi i rezervimit */
    private String reference;

    /** ID e tenantit për mbështetje në arkitekturë multi-tenant */
    private String tenantId;

    // Getters dhe Setters të përcaktuara manualisht

    /**
     * Merr ID-në e pagesës.
     *
     * @return ID si {@code Long}
     */
    public Long getId() {
        return id;
    }

    /**
     * Vendos ID-në e pagesës.
     *
     * @param id ID e re
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Merr metodën e përdorur për pagesë.
     *
     * @return metoda si tekst (p.sh., "PAYPAL")
     */
    public String getMethod() {
        return method;
    }

    /**
     * Vendos metodën e pagesës.
     *
     * @param method metoda e re
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
     * Merr kohën e pagesës.
     *
     * @return koha si {@code LocalDateTime}
     */
    public LocalDateTime getPaymentTime() {
        return paymentTime;
    }

    /**
     * Vendos kohën e pagesës.
     *
     * @param paymentTime koha e re e pagesës
     */
    public void setPaymentTime(LocalDateTime paymentTime) {
        this.paymentTime = paymentTime;
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
     * @param status statusi i ri
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Merr referencën e pagesës.
     *
     * @return referenca si tekst
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
     * Merr tenantId-in për të identifikuar organizatën që lidhet me këtë pagesë.
     *
     * @return tenantId si tekst
     */
    public String getTenantId() {
        return tenantId;
    }

    /**
     * Vendos tenantId-in për këtë pagesë.
     *
     * @param tenantId tenantId i ri
     */
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
