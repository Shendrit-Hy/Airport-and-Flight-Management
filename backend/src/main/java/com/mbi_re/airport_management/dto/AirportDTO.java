package com.mbi_re.airport_management.dto;

/**
 * DTO (Data Transfer Object) për përfaqësimin e një aeroporti.
 * Përdoret për transferimin e të dhënave të aeroportit midis shtresave të aplikacionit.
 */
public class AirportDTO {

    /** Emri i aeroportit */
    private String name;

    /** Kodi unik i aeroportit (p.sh., PRN, JFK) */
    private String code;

    /** ID e vendit ku ndodhet aeroporti */
    private Long countryId;

    /** ID e qytetit ku ndodhet aeroporti */
    private Long cityId;

    /** Zona kohore e aeroportit (p.sh., Europe/Pristina) */
    private String timezone;

    /** ID e tenantit për qëllime të izolimit multi-tenant */
    private String tenantId;

    /**
     * Merr emrin e aeroportit.
     *
     * @return emri i aeroportit
     */
    public String getName() {
        return name;
    }

    /**
     * Vendos emrin e aeroportit.
     *
     * @param name emri i ri i aeroportit
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Merr kodin e aeroportit.
     *
     * @return kodi i aeroportit
     */
    public String getCode() {
        return code;
    }

    /**
     * Vendos kodin e aeroportit.
     *
     * @param code kodi i ri i aeroportit
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Merr ID e vendit ku ndodhet aeroporti.
     *
     * @return ID e vendit
     */
    public Long getCountryId() {
        return countryId;
    }

    /**
     * Vendos ID e vendit ku ndodhet aeroporti.
     *
     * @param countryId ID e re e vendit
     */
    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    /**
     * Merr ID e qytetit ku ndodhet aeroporti.
     *
     * @return ID e qytetit
     */
    public Long getCityId() {
        return cityId;
    }

    /**
     * Vendos ID e qytetit ku ndodhet aeroporti.
     *
     * @param cityId ID e re e qytetit
     */
    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    /**
     * Merr zonën kohore të aeroportit.
     *
     * @return zona kohore si tekst
     */
    public String getTimezone() {
        return timezone;
    }

    /**
     * Vendos zonën kohore të aeroportit.
     *
     * @param timezone zona e re kohore
     */
    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    /**
     * Merr ID e tenantit për këtë aeroport.
     *
     * @return tenantId si tekst
     */
    public String getTenantId() {
        return tenantId;
    }

    /**
     * Vendos ID e tenantit për këtë aeroport.
     *
     * @param tenantId ID e re e tenantit
     */
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
