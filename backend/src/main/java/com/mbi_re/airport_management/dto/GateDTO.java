package com.mbi_re.airport_management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO (Data Transfer Object) për përfaqësimin e një porte (gate) në aeroport.
 * Përdoret për transferimin e të dhënave të portave midis shtresave të aplikacionit.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GateDTO {

    /** ID unike e portës */
    private Long id;

    /** Numri i portës (p.sh., G1, A12) */
    private String gateNumber;

    /** Statusi i portës (p.sh., AKTIVE, E BLLOKUAR) */
    private String status;

    /** ID e terminalit ku ndodhet kjo portë */
    private Long terminalId;

    /** ID e fluturimit të lidhur me këtë portë (nëse ka) */
    private Long flightId;

    /** ID e tenantit për sistemin multi-tenant */
    private String tenantId;

    /**
     * Merr ID e portës.
     *
     * @return ID si {@code Long}
     */
    public Long getId() {
        return id;
    }

    /**
     * Vendos ID e portës.
     *
     * @param id ID e re
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Merr numrin e portës.
     *
     * @return numri i portës
     */
    public String getGateNumber() {
        return gateNumber;
    }

    /**
     * Vendos numrin e portës.
     *
     * @param gateNumber numri i ri i portës
     */
    public void setGateNumber(String gateNumber) {
        this.gateNumber = gateNumber;
    }

    /**
     * Merr statusin e portës.
     *
     * @return statusi i portës
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
     * Merr ID e terminalit.
     *
     * @return ID e terminalit
     */
    public Long getTerminalId() {
        return terminalId;
    }

    /**
     * Vendos ID e terminalit.
     *
     * @param terminalId ID e re e terminalit
     */
    public void setTerminalId(Long terminalId) {
        this.terminalId = terminalId;
    }

    /**
     * Merr ID e fluturimit të lidhur me këtë portë.
     *
     * @return ID e fluturimit
     */
    public Long getFlightId() {
        return flightId;
    }

    /**
     * Vendos ID e fluturimit të lidhur me këtë portë.
     *
     * @param flightId ID e re e fluturimit
     */
    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }

    /**
     * Merr tenantId-in e lidhur me këtë portë.
     *
     * @return tenantId si tekst
     */
    public String getTenantId() {
        return tenantId;
    }

    /**
     * Vendos tenantId-in për këtë portë.
     *
     * @param tenantId tenantId i ri
     */
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
