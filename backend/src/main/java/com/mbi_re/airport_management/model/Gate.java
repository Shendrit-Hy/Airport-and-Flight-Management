package com.mbi_re.airport_management.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

/**
 * Entiteti {@code Gate} përfaqëson një portë në një terminal aeroporti.
 * Porta është e lidhur me një fluturim (opsionale) dhe një terminal (e detyrueshme),
 * dhe mund të ketë një status të caktuar (p.sh., OPEN, CLOSED).
 */
@Entity
public class Gate {

    /** ID unike e portës, e gjeneruar automatikisht nga databaza */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Numri i portës (p.sh., "A5", "B12") */
    private String gateNumber;

    /** Statusi i portës (p.sh., OPEN, CLOSED, OCCUPIED) */
    private String status;

    /** Terminali ku ndodhet kjo portë (lidhje e detyrueshme) */
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "terminal_id", nullable = false)
    private Terminal terminal;

    /** Fluturimi që është caktuar aktualisht në këtë portë (lidhje opsionale) */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "flight_id")
    private Flight flight;

    /** ID e tenantit për mbështetje në sistemin multi-tenant */
    private String tenantId;

    // Getters & Setters

    /**
     * Merr ID-në e portës.
     *
     * @return ID si {@code Long}
     */
    public Long getId() {
        return id;
    }

    /**
     * Vendos ID-në e portës.
     *
     * @param id ID e re
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Merr numrin e portës.
     *
     * @return numri i portës si tekst
     */
    public String getGateNumber() {
        return gateNumber;
    }

    /**
     * Vendos numrin e portës.
     *
     * @param gateNumber numri i ri
     */
    public void setGateNumber(String gateNumber) {
        this.gateNumber = gateNumber;
    }

    /**
     * Merr statusin aktual të portës.
     *
     * @return statusi si tekst
     */
    public String getStatus() {
        return status;
    }

    /**
     * Vendos statusin e portës.
     *
     * @param status statusi i ri
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Merr terminalin ku ndodhet porta.
     *
     * @return objekti {@code Terminal}
     */
    public Terminal getTerminal() {
        return terminal;
    }

    /**
     * Vendos terminalin për këtë portë.
     *
     * @param terminal objekti {@code Terminal}
     */
    public void setTerminal(Terminal terminal) {
        this.terminal = terminal;
    }

    /**
     * Merr fluturimin e lidhur me këtë portë (nëse ka).
     *
     * @return objekti {@code Flight}
     */
    public Flight getFlight() {
        return flight;
    }

    /**
     * Vendos fluturimin për këtë portë.
     *
     * @param flight objekti {@code Flight}
     */
    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    /**
     * Merr tenantId-in që identifikon organizatën.
     *
     * @return tenantId si tekst
     */
    public String getTenantId() {
        return tenantId;
    }

    /**
     * Vendos tenantId-in për këtë portë.
     *
     * @param tenantId ID e re e tenantit
     */
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
