package com.mbi_re.airport_management.dto;

import com.mbi_re.airport_management.model.FlightStatus;
import com.mbi_re.airport_management.model.Gate;
import com.mbi_re.airport_management.model.Terminal;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * DTO (Data Transfer Object) për përfaqësimin e një fluturimi.
 * Përdoret për transferimin e të dhënave të fluturimeve midis shtresave të aplikacionit.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlightDTO {

    /** ID unike e fluturimit */
    private Long id;

    /** Numri i fluturimit (p.sh., W64001) */
    private String flightNumber;

    /** Aeroporti i nisjes */
    private String departureAirport;

    /** Aeroporti i mbërritjes */
    private String arrivalAirport;

    /** Ora e nisjes së fluturimit */
    private LocalTime departureTime;

    /** Ora e mbërritjes së fluturimit */
    private LocalTime arrivalTime;

    /** Data e fluturimit */
    private LocalDate flightDate;

    /** Numri i ulëseve të lira */
    private int availableSeat;

    /** Çmimi i biletës së fluturimit */
    private double price;

    /** Statusi i fluturimit (p.sh., ON_TIME, DELAYED, CANCELLED) */
    private FlightStatus flightStatus;

    /** ID e tenantit për sistemin multi-tenant */
    private String tenantId;

    /** ID e portës (gate) së fluturimit */
    private Long gateId;

    /** ID e terminalit ku nis apo mbërrin fluturimi */
    private Long terminalId;

    /** ID e linjës ajrore që kryen fluturimin */
    private Long airlineId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getDepartureAirport() {
        return departureAirport;
    }

    public void setDepartureAirport(String departureAirport) {
        this.departureAirport = departureAirport;
    }

    public String getArrivalAirport() {
        return arrivalAirport;
    }

    public void setArrivalAirport(String arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public LocalDate getFlightDate() {
        return flightDate;
    }

    public void setFlightDate(LocalDate flightDate) {
        this.flightDate = flightDate;
    }

    public int getAvailableSeat() {
        return availableSeat;
    }

    public void setAvailableSeat(int availableSeat) {
        this.availableSeat = availableSeat;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public FlightStatus getFlightStatus() {
        return flightStatus;
    }

    public void setFlightStatus(FlightStatus flightStatus) {
        this.flightStatus = flightStatus;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public Long getGateId() {
        return gateId;
    }

    public void setGateId(Long gateId) {
        this.gateId = gateId;
    }

    public Long getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(Long terminalId) {
        this.terminalId = terminalId;
    }

    public Long getAirlineId() {
        return airlineId;
    }

    public void setAirlineId(Long airlineId) {
        this.airlineId = airlineId;
    }
}
