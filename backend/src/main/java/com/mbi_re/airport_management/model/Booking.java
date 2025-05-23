package com.mbi_re.airport_management.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Entiteti {@code Booking} përfaqëson një rezervim për një fluturim në sistemin e menaxhimit të fluturimeve.
 * Ai përmban informacion për pasagjerin, fluturimin, ulësen, kohën e rezervimit dhe statusin e rezervimit.
 */
@Entity
@Table(name = "bookings")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booking {

    /** ID unike e rezervimit, gjeneruar automatikisht */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Emri i pasagjerit që ka bërë rezervimin */
    private String passengerName;

    /** ID e pasagjerit në sistem */
    private Long passengerId;

    /** Numri i fluturimit që është rezervuar */
    private String flightNumber;

    /** Numri i ulëses së rezervuar */
    private String seatNumber;

    /** Data dhe ora kur është bërë rezervimi */
    private LocalDateTime bookingTime;

    /** ID e rezervimit për qëllime referimi të jashtme */
    @Column(name = "booking_id")
    private String bookingId;

    /** Statusi i rezervimit (p.sh., BOOKED, CANCELLED) */
    private String status;

    /** ID e tenantit për mbështetjen e sistemit multi-tenant */
    @Column(nullable = false)
    private String tenantId;

    /** Flaga që tregon nëse pasagjeri është check-in apo jo */
    private boolean checkedIn;

    /**
     * Kontrollon nëse pasagjeri ka kryer check-in.
     *
     * @return true nëse është bërë check-in, false përndryshe
     */
    public boolean isCheckedIn() {
        return checkedIn;
    }

    /**
     * Vendos statusin e check-in-it për pasagjerin.
     *
     * @param checkedIn vlera e re e check-in-it
     */
    public void setCheckedIn(boolean checkedIn) {
        this.checkedIn = checkedIn;
    }

    /**
     * Merr ID-në e rezervimit (jo primare, por e përdorur për referenca).
     *
     * @return bookingId si tekst
     */
    public String getBookingId() {
        return bookingId;
    }

    /**
     * Vendos ID-në e rezervimit për referenca të jashtme.
     *
     * @param bookingId ID e re
     */
    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    /**
     * Merr ID-në e pasagjerit të lidhur me këtë rezervim.
     *
     * @return ID e pasagjerit
     */
    public Long getPassengerId() {
        return passengerId;
    }

    /**
     * Vendos ID-në e pasagjerit të lidhur me këtë rezervim.
     *
     * @param passengerId ID e re e pasagjerit
     */
    public void setPassengerId(Long passengerId) {
        this.passengerId = passengerId;
    }

    /**
     * Merr ID-në e rezervimit.
     *
     * @return ID si {@code Long}
     */
    public Long getId() {
        return id;
    }

    /**
     * Vendos ID-në e rezervimit.
     *
     * @param id ID e re
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Merr emrin e pasagjerit.
     *
     * @return emri i pasagjerit
     */
    public String getPassengerName() {
        return passengerName;
    }

    /**
     * Vendos emrin e pasagjerit.
     *
     * @param passengerName emri i ri
     */
    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    /**
     * Merr numrin e fluturimit të rezervuar.
     *
     * @return numri i fluturimit
     */
    public String getFlightNumber() {
        return flightNumber;
    }

    /**
     * Vendos numrin e fluturimit të rezervuar.
     *
     * @param flightNumber numri i ri i fluturimit
     */
    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    /**
     * Merr numrin e ulëses së rezervuar.
     *
     * @return numri i ulëses
     */
    public String getSeatNumber() {
        return seatNumber;
    }

    /**
     * Vendos numrin e ulëses së rezervuar.
     *
     * @param seatNumber ulësja e re
     */
    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    /**
     * Merr kohën kur është bërë rezervimi.
     *
     * @return koha e rezervimit
     */
    public LocalDateTime getBookingTime() {
        return bookingTime;
    }

    /**
     * Vendos kohën kur është bërë rezervimi.
     *
     * @param bookingTime koha e re e rezervimit
     */
    public void setBookingTime(LocalDateTime bookingTime) {
        this.bookingTime = bookingTime;
    }

    /**
     * Merr statusin e rezervimit.
     *
     * @return statusi (p.sh., BOOKED, CANCELLED)
     */
    public String getStatus() {
        return status;
    }

    /**
     * Vendos statusin e rezervimit.
     *
     * @param status statusi i ri
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Merr tenantId-in për këtë rezervim.
     *
     * @return tenantId si tekst
     */
    public String getTenantId() {
        return tenantId;
    }

    /**
     * Vendos tenantId-in për këtë rezervim.
     *
     * @param tenantId tenantId i ri
     */
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
