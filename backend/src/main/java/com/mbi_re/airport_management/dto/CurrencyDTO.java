package com.mbi_re.airport_management.dto;

/**
 * DTO (Data Transfer Object) për përfaqësimin e një monedhe.
 * Përdoret për transferimin e të dhënave të monedhës midis shtresave të aplikacionit.
 */
public class CurrencyDTO {

    /** Kodi i monedhës (p.sh., EUR, USD, GBP) */
    private String code;

    /** Emri i monedhës (p.sh., Euro, Dollar) */
    private String name;

    /** Konstruktori i zbrazët i nevojshëm për deserializim */
    public CurrencyDTO() {}

    /**
     * Konstruktori me parametra për inicializimin e objektit CurrencyDTO.
     *
     * @param code kodi i monedhës
     * @param name emri i monedhës
     */
    public CurrencyDTO(String code, String name) {
        this.code = code;
        this.name = name;
    }

    /**
     * Merr kodin e monedhës.
     *
     * @return kodi i monedhës
     */
    public String getCode() {
        return code;
    }

    /**
     * Vendos kodin e monedhës.
     *
     * @param code kodi i ri i monedhës
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Merr emrin e monedhës.
     *
     * @return emri i monedhës
     */
    public String getName() {
        return name;
    }

    /**
     * Vendos emrin e monedhës.
     *
     * @param name emri i ri i monedhës
     */
    public void setName(String name) {
        this.name = name;
    }
}
