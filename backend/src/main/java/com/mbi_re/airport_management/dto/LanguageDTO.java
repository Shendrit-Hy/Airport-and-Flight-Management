package com.mbi_re.airport_management.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO (Data Transfer Object) për përfaqësimin e një gjuhe të mbështetur në sistem.
 * Përdoret për të transferuar të dhëna të gjuhëve si pjesë e konfigurimit multigjuhësor.
 */
public class LanguageDTO {

    /** Emri i gjuhës (p.sh., English, Shqip) */
    @NotBlank(message = "Language name is required")
    private String name;

    /** Kodi i gjuhës (p.sh., en, sq, de) */
    @NotBlank(message = "Language code is required")
    private String code;

    /** Konstruktori i zbrazët i nevojshëm për serializim/deserializim */
    public LanguageDTO() {
    }

    /**
     * Konstruktori me parametra për inicializimin e objektit LanguageDTO.
     *
     * @param name emri i gjuhës
     * @param code kodi i gjuhës
     */
    public LanguageDTO(String name, String code) {
        this.name = name;
        this.code = code;
    }

    /**
     * Merr emrin e gjuhës.
     *
     * @return emri i gjuhës
     */
    public String getName() {
        return name;
    }

    /**
     * Vendos emrin e gjuhës.
     *
     * @param name emri i ri i gjuhës
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Merr kodin e gjuhës.
     *
     * @return kodi i gjuhës
     */
    public String getCode() {
        return code;
    }

    /**
     * Vendos kodin e gjuhës.
     *
     * @param code kodi i ri i gjuhës
     */
    public void setCode(String code) {
        this.code = code;
    }
}
