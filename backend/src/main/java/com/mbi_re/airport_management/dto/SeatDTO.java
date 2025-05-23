package com.mbi_re.airport_management.dto;

/**
 * DTO (Data Transfer Object) për përfaqësimin e një ulëseje të një fluturimi.
 * Përdoret për transferimin e të dhënave të ulëseve midis shtresave të aplikacionit.
 */
public class SeatDTO {

    /** ID unike e ulëses */
    private Long id;

    /** Numri i ulëses (p.sh., 12A, 14C) */
    private String seatNumber;

    /** ID e fluturimit që i përket kjo ulëse */
    private Long flightId;

    /** Numri i fluturimit (p.sh., W64001) */
    private String flightNumber;

    /** ID e tenantit për të mbështetur multi-tenancy */
    private String tenantId;

    /** Tregon nëse ulësja është rezervuar apo jo */
    private boolean booked;

    /** Konstruktori i zbrazët për deserializim */
    public SeatDTO() {}

    /**
     * Konstruktori me parametra për të krijuar një objekt të plotë të SeatDTO.
     *
     * @param id ID e ulëses
     * @param seatNumber numri i ulëses
     * @param flightId ID e fluturimit
     * @param flightNumber numri i fluturimit
     * @param tenantId ID e tenantit
     * @param booked statusi i rezervimit
     */
    public SeatDTO(Long id, String seatNumber, Long flightId, String flightNumber, String tenantId, boolean booked) {
        this.id = id;
        this.seatNumber = seatNumber;
        this.flightId = flightId;
        this.flightNumber = flightNumber;
        this.tenantId = tenantId;
        this.booked = booked;
    }

    /**
     * Merr ID-në e ulëses.
     *
     * @return ID e ulëses
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
     * @return numri i ulëses
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
     * Merr ID e fluturimit të lidhur me këtë ulëse.
     *
     * @return ID e fluturimit
     */
    public Long getFlightId() {
        return flightId;
    }

    /**
     * Vendos ID e fluturimit të lidhur me këtë ulëse.
     *
     * @param flightId ID e re e fluturimit
     */
    public void setFlightId(Long flightId) {
        this.flightId = flightId;
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
     * Merr tenantId-in për këtë ulëse.
     *
     * @return tenantId si tekst
     */
    public String getTenantId() {
        return tenantId;
    }

    /**
     * Vendos tenantId-in për këtë ulëse.
     *
     * @param tenantId tenantId i ri
     */
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    /**
     * Kontrollon nëse ulësja është rezervuar.
     *
     * @return true nëse është rezervuar, përndryshe false
     */
    public boolean isBooked() {
        return booked;
    }

    /**
     * Vendos statusin e rezervimit të ulëses.
     *
     * @param booked true nëse është rezervuar
     */
    public void setBooked(boolean booked) {
        this.booked = booked;
    }
}
