package com.mbi_re.airport_management.dto;

/**
 * DTO (Data Transfer Object) për përfaqësimin e një qyteti.
 * Kjo klasë përdoret për transferimin e të dhënave të qyteteve midis shtresave të aplikacionit.
 */
public class CityDTO {

    /** ID unike e qytetit */
    private Long id;

    /** Emri i qytetit */
    private String name;

    /** ID e vendit ku ndodhet qyteti */
    private Long countryId;

    /** Konstruktori i zbrazët i nevojshëm për deserializim */
    public CityDTO() {}

    /**
     * Konstruktori me parametra për inicializimin e objektit CityDTO.
     *
     * @param id        ID e qytetit
     * @param name      Emri i qytetit
     * @param countryId ID e vendit ku ndodhet qyteti
     */
    public CityDTO(Long id, String name, Long countryId) {
        this.id = id;
        this.name = name;
        this.countryId = countryId;
    }

    /**
     * Merr ID e qytetit.
     *
     * @return ID e qytetit
     */
    public Long getId() {
        return id;
    }

    /**
     * Vendos ID e qytetit.
     *
     * @param id ID e re e qytetit
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Merr emrin e qytetit.
     *
     * @return emri i qytetit
     */
    public String getName() {
        return name;
    }

    /**
     * Vendos emrin e qytetit.
     *
     * @param name emri i ri i qytetit
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Merr ID e vendit ku ndodhet qyteti.
     *
     * @return ID e vendit
     */
    public Long getCountryId() {
        return countryId;
    }

    /**
     * Vendos ID e vendit ku ndodhet qyteti.
     *
     * @param countryId ID e re e vendit
     */
    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }
}
