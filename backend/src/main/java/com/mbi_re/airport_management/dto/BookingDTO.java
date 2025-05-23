package com.mbi_re.airport_management.dto;

import lombok.Data;

/**
 * DTO (Data Transfer Object) për përfaqësimin e një rezervimi të bërë nga një pasagjer.
 * Kjo klasë përdoret për të transferuar të dhëna rreth rezervimeve midis shtresave të aplikacionit.
 */
@Data
public class BookingDTO {

    /** Emri i pasagjerit që ka bërë rezervimin */
    private String passengerName;

    /** Numri i fluturimit që lidhet me rezervimin */
    private String flightNumber;

    /** ID unike e rezervimit */
    private String bookingId;

    /** ID e pasagjerit të lidhur me rezervimin */
    private Long passengerId;

    /** Numri i ulëses së rezervuar nga pasagjeri */
    private String seatNumber;

    /** Statusi aktual i rezervimit (p.sh., Confirmed, Cancelled) */
    private String status;

    /** ID e tenantit për sistemin multi-tenant */
    private String tenantId;

    /** Tregon nëse pasagjeri ka bërë check-in apo jo */
    private boolean checkedIn;

    /**
     * Kontrollon nëse pasagjeri është check-in.
     *
     * @return true nëse është check-in, përndryshe false
     */
    public boolean isCheckedIn() {
        return checkedIn;
    }

    /**
     * Vendos statusin e check-in për pasagjerin.
     *
     * @param checkedIn true nëse është check-in
     */
    public void setCheckedIn(boolean checkedIn) {
        this.checkedIn = checkedIn;
    }

    /**
     * Merr ID e rezervimit.
     *
     * @return bookingId si tekst
     */
    public String getBookingId() {
        return bookingId;
    }

    /**
     * Vendos ID e rezervimit.
     *
     * @param bookingId ID e re e rezervimit
     */
    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    /**
     * Merr ID e pasagjerit.
     *
     * @return ID e pasagjerit
     */
    public Long getPassengerId() {
        return passengerId;
    }

    /**
     * Vendos ID e pasagjerit.
     *
     * @param passengerId ID e re e pasagjerit
     */
    public void setPassengerId(Long passengerId) {
        this.passengerId = passengerId;
    }

    /**
     * Merr tenantId për këtë rezervim.
     *
     * @return ID e tenantit
     */
    public String getTenantId() {
        return tenantId;
    }

    /**
     * Vendos tenantId për këtë rezervim.
     *
     * @param tenantId ID e re e tenantit
     */
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
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
     * @param passengerName emri i ri i pasagjerit
     */
    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    /**
     * Merr numrin e fluturimit.
     *
     * @return numri i fluturimit
     */
    public String getFlightNumber() {
        return flightNumber;
    }

    /**
     * Vendos numrin e fluturimit.
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
     * @param seatNumber numri i ri i ulëses
     */
    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    /**
     * Merr statusin e rezervimit.
     *
     * @return statusi i rezervimit
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
}
