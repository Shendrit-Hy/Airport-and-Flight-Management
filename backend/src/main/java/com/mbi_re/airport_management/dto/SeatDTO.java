package com.mbi_re.airport_management.dto;

public class SeatDTO {
    private Long id;
    private String seatNumber;
    private Long flightId;
    private String flightNumber;
    private String tenantId;
    private boolean booked;

    public SeatDTO() {}

    public SeatDTO(Long id, String seatNumber, Long flightId, String flightNumber, String tenantId, boolean booked) {
        this.id = id;
        this.seatNumber = seatNumber;
        this.flightId = flightId;
        this.flightNumber = flightNumber;
        this.tenantId = tenantId;
        this.booked = booked;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public Long getFlightId() {
        return flightId;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public boolean isBooked() {
        return booked;
    }

    public void setBooked(boolean booked) {
        this.booked = booked;
    }
}
