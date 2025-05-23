package com.mbi_re.airport_management.dto;

import lombok.Data;

/**
 * DTO për përgjigjen e të dhënave të një porte (gate),
 * e cila përfshin informacionin për portën dhe terminalin përkatës.
 * Përdoret në shfaqjen e të dhënave për përdorues ose admin në UI.
 */
@Data
public class GateResponseDTO {

    /** ID unike e portës */
    private Long id;

    /** Numri i portës (p.sh., G2, A15) */
    private String gateNumber;

    /** Statusi i portës (p.sh., AKTIVE, E ZËNË) */
    private String status;

    /** Emri i terminalit që përmban këtë portë */
    private String terminalName;

    /** ID e terminalit që lidhet me këtë portë */
    private Long terminalId;

    /** ID e fluturimit të lidhur me këtë portë (nëse ka) */
    private Long flightId;

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
     * @param status statusi i ri i portës
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Merr emrin e terminalit ku ndodhet porta.
     *
     * @return emri i terminalit
     */
    public String getTerminalName() {
        return terminalName;
    }

    /**
     * Vendos emrin e terminalit ku ndodhet porta.
     *
     * @param terminalName emri i ri i terminalit
     */
    public void setTerminalName(String terminalName) {
        this.terminalName = terminalName;
    }

    /**
     * Merr ID-në e terminalit.
     *
     * @return ID e terminalit
     */
    public Long getTerminalId() {
        return terminalId;
    }

    /**
     * Vendos ID-në e terminalit.
     *
     * @param terminalId ID e re e terminalit
     */
    public void setTerminalId(Long terminalId) {
        this.terminalId = terminalId;
    }

    /**
     * Merr ID-në e fluturimit të lidhur me këtë portë.
     *
     * @return ID e fluturimit
     */
    public Long getFlightId() {
        return flightId;
    }

    /**
     * Vendos ID-në e fluturimit të lidhur me këtë portë.
     *
     * @param flightId ID e re e fluturimit
     */
    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }
}
