package com.mbi_re.airport_management.dto;

import lombok.Data;

/**
 * DTO (Data Transfer Object) për përfaqësimin e një zone kohore.
 * Përdoret për të transferuar të dhëna të lidhura me zonat kohore në sistemin e menaxhimit të aeroporteve.
 */
@Data
public class TimeZoneDTO {

    /** Identifikuesi i zonës kohore (p.sh., "Europe/Berlin", "Asia/Kolkata") */
    private String id;

    /** Offset-i i zonës kohore ndaj UTC (p.sh., "UTC+01:00", "UTC+05:30") */
    private String offset;

    /** Konstruktori i zbrazët për nevojat e deserializimit */
    public TimeZoneDTO() {}

    /**
     * Konstruktori me parametra për inicializimin e një objekti TimeZoneDTO.
     *
     * @param id identifikuesi i zonës kohore
     * @param offset zhvendosja ndaj UTC
     */
    public TimeZoneDTO(String id, String offset) {
        this.id = id;
        this.offset = offset;
    }

    /**
     * Merr identifikuesin e zonës kohore.
     *
     * @return id si {@code String}
     */
    public String getId() {
        return id;
    }

    /**
     * Vendos identifikuesin e zonës kohore.
     *
     * @param id id e re e zonës kohore
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Merr offset-in e zonës kohore ndaj UTC.
     *
     * @return offset si {@code String}
     */
    public String getOffset() {
        return offset;
    }

    /**
     * Vendos offset-in e zonës kohore ndaj UTC.
     *
     * @param offset offset i ri
     */
    public void setOffset(String offset) {
        this.offset = offset;
    }
}
