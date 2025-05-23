package com.mbi_re.airport_management.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entiteti {@code Seat} përfaqëson një ulëse në një fluturim specifik.
 * Ulësja është e lidhur me një fluturim dhe përfshin informacione si numri i ulëses,
 * statusi i rezervimit, dhe identifikuesi i tenantit për mbështetjen e multi-tenancy.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Seat {

    /** ID unike e ulëses, e gjeneruar automatikisht nga databaza */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Numri i ulëses (p.sh., "12A", "3B") */
    private String seatNumber;

    /** Fluturimi të cilit i përket kjo ulëse */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flight_id")
    private Flight flight;

    /** ID e tenantit për mbështetje në sistemin multi-tenant */
    private String tenantId;

    /** Statusi që tregon nëse ulësja është e rezervuar apo jo */
    @Column(name = "booked")
    private boolean booked;

    /**
     * Kontrollon nëse ulësja është e rezervuar.
     *
     * @return {@code true} nëse është e rezervuar, përndryshe {@code false}
     */
    public boolean isBooked() {
        return booked;
    }

    /**
     * Vendos statusin e rezervimit të ulëses.
     *
     * @param booked {@code true} nëse është rezervuar, {@code false} për lirë
     */
    public void setBooked(boolean booked) {
        this.booked = booked;
    }

    /**
     * Merr ID-në e ulëses.
     *
     * @return ID si {@code Long}
     */
    public Long getId() {
        return id;
    }

    /**
     * Vendos ID-në e ulëses.
     *
     * @param id ID e re
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Merr numrin e ulëses.
     *
     * @return numri si tekst
     */
    public String getSeatNumber() {
        return seatNumber;
    }

    /**
     * Vendos numrin e ulëses.
     *
     * @param seatNumber numri i ri i ulëses
     */
    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    /**
     * Merr fluturimin të cilit i përket ulësja.
     *
     * @return objekti {@code Flight}
     */
    public Flight getFlight() {
        return flight;
    }

    /**
     * Vendos fluturimin për këtë ulëse.
     *
     * @param flight objekti {@code Flight}
     */
    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    /**
     * Merr tenantId-in e organizatës që ka këtë ulëse.
     *
     * @return tenantId si tekst
     */
    public String getTenantId() {
        return tenantId;
    }

    /**
     * Vendos tenantId-in e organizatës që ka këtë ulëse.
     *
     * @param tenantId tenantId i ri
     */
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
