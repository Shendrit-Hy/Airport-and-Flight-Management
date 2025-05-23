package com.mbi_re.airport_management.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Entiteti {@code Flight} përfaqëson një fluturim në sistemin e menaxhimit të aeroporteve.
 * Ai përmban informacion të plotë për nisjen, mbërritjen, kohët, çmimin, statusin dhe lidhjet me entitete të tjera si Gate, Terminal dhe Airline.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Flight {

    /** ID unike e fluturimit, e gjeneruar automatikisht */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Numri unik i fluturimit (p.sh., "MB123") */
    private String flightNumber;

    /** Aeroporti i nisjes (emri i qytetit ose kodi) */
    private String departureAirport;

    /** Aeroporti i mbërritjes (emri i qytetit ose kodi) */
    private String arrivalAirport;

    /** Ora e nisjes së fluturimit */
    private LocalTime departureTime;

    /** Ora e mbërritjes së fluturimit */
    private LocalTime arrivalTime;

    /** Data kur do të zhvillohet fluturimi */
    private LocalDate flightDate;

    /** Numri i ulëseve të disponueshme për këtë fluturim */
    private int availableSeat;

    /** Çmimi i një bilete për këtë fluturim */
    private double price;

    /** Statusi aktual i fluturimit (p.sh., ON_TIME, DELAYED, CANCELLED) */
    @Enumerated(EnumType.STRING)
    private FlightStatus flightStatus;

    /** ID e tenantit për të mbështetur multi-tenancy */
    @Column(name = "tenant_id", nullable = false)
    private String tenantId;

    /** Porta (Gate) nga ku niset fluturimi (opsionale) */
    @ManyToOne
    @JoinColumn(name = "gate_id")
    private Gate gate;

    /** Terminali i nisjes së fluturimit (opsional) */
    @ManyToOne
    @JoinColumn(name = "terminal_id")
    private Terminal terminal;

    /** Kompania ajrore që operon këtë fluturim */
    @ManyToOne
    @JoinColumn(name = "airline_id", nullable = false)
    private Airline airline;

    // Getters dhe Setters të manualizuar

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

    public Gate getGate() {
        return gate;
    }

    public void setGate(Gate gate) {
        this.gate = gate;
    }

    public Terminal getTerminal() {
        return terminal;
    }

    public void setTerminal(Terminal terminal) {
        this.terminal = terminal;
    }

    public Airline getAirline() {
        return airline;
    }

    public void setAirline(Airline airline) {
        this.airline = airline;
    }
}
