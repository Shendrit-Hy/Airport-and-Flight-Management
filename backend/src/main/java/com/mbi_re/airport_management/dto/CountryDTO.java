package com.mbi_re.airport_management.dto;

/**
 * DTO (Data Transfer Object) për përfaqësimin e një shteti.
 * Kjo klasë përdoret për të transferuar të dhënat e shteteve midis shtresave të aplikacionit.
 */
public class CountryDTO {

    /** ID unike e shtetit */
    private Long id;

    /** Emri i shtetit */
    private String name;

    /** Kodi i shtetit (p.sh., AL, US, DE) */
    private String code;

    /**
     * Kthen kodin e shtetit.
     *
     * @return kodi i shtetit
     */
    public String getCode() {
        return code;
    }

    /**
     * Vendos kodin e shtetit.
     *
     * @param code kodi i ri i shtetit
     */
    public void setCode(String code) {
        this.code = code;
    }

    /** Konstruktori i zbrazët për deserializim */
    public CountryDTO() {
    }

    /**
     * Konstruktori me parametra për të krijuar një objekt CountryDTO.
     *
     * @param id   ID e shtetit
     * @param name Emri i shtetit
     */
    public CountryDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Merr ID e shtetit.
     *
     * @return ID e shtetit
     */
    public Long getId() {
        return id;
    }

    /**
     * Vendos ID e shtetit.
     *
     * @param id ID e re e shtetit
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Merr emrin e shtetit.
     *
     * @return emri i shtetit
     */
    public String getName() {
        return name;
    }

    /**
     * Vendos emrin e shtetit.
     *
     * @param name emri i ri i shtetit
     */
    public void setName(String name) {
        this.name = name;
    }
}
