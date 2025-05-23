package com.mbi_re.airport_management.dto;

/**
 * DTO (Data Transfer Object) për përfaqësimin e një terminali në një aeroport.
 * Përdoret për transferimin e të dhënave të terminaleve midis shtresave të aplikacionit.
 */
public class TerminalDTO {

    /** ID unike e terminalit */
    private Long id;

    /** Emri i terminalit (p.sh., Terminal A, Terminal 2) */
    private String name;

    /** ID e aeroportit që përmban këtë terminal */
    private Long airportId;

    /** ID e tenantit për mbështetjen e multi-tenancy */
    private String tenantId;

    /** Konstruktori i zbrazët për deserializim */
    public TerminalDTO() {}

    /**
     * Konstruktori me parametra për të inicializuar një objekt TerminalDTO.
     *
     * @param id ID e terminalit
     * @param name emri i terminalit
     * @param airportId ID e aeroportit
     * @param tenantId ID e tenantit
     */
    public TerminalDTO(Long id, String name, Long airportId, String tenantId) {
        this.id = id;
        this.name = name;
        this.airportId = airportId;
        this.tenantId = tenantId;
    }

    /**
     * Merr ID-në e terminalit.
     *
     * @return ID si {@code Long}
     */
    public Long getId() {
        return id;
    }

    /**
     * Vendos ID-në e terminalit.
     *
     * @param id ID e re
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Merr emrin e terminalit.
     *
     * @return emri i terminalit
     */
    public String getName() {
        return name;
    }

    /**
     * Vendos emrin e terminalit.
     *
     * @param name emri i ri i terminalit
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Merr ID-në e aeroportit të lidhur me këtë terminal.
     *
     * @return ID e aeroportit
     */
    public Long getAirportId() {
        return airportId;
    }

    /**
     * Vendos ID-në e aeroportit të lidhur me këtë terminal.
     *
     * @param airportId ID e re e aeroportit
     */
    public void setAirportId(Long airportId) {
        this.airportId = airportId;
    }

    /**
     * Merr tenantId-in për këtë terminal.
     *
     * @return tenantId si tekst
     */
    public String getTenantId() {
        return tenantId;
    }

    /**
     * Vendos tenantId-in për këtë terminal.
     *
     * @param tenantId tenantId i ri
     */
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
