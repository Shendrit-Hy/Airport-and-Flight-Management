package com.mbi_re.airport_management.model;

import jakarta.persistence.*;

/**
 * Entiteti {@code Airport} përfaqëson një aeroport në sistemin e menaxhimit të fluturimeve.
 * Kjo klasë përfshin informacionin bazë për aeroportin dhe lidhet me entitetet {@code Country} dhe {@code City}.
 */
@Entity
public class Airport {

    /** ID unike e aeroportit, gjeneruar automatikisht */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Emri i aeroportit (p.sh., "Adem Jashari International Airport") */
    private String name;

    /** Kodi unik i aeroportit (p.sh., "PRN") */
    private String code;

    /** Vendi ku ndodhet aeroporti */
    @ManyToOne
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;

    /** Qyteti ku ndodhet aeroporti */
    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    /** Zona kohore e aeroportit (p.sh., "Europe/Pristina") */
    private String timezone;

    /** ID e tenantit për mbështetje të multi-tenancy */
    private String tenantId;

    /**
     * Merr ID-në e aeroportit.
     *
     * @return ID si {@code Long}
     */
    public Long getId() {
        return id;
    }

    /**
     * Vendos ID-në e aeroportit.
     *
     * @param id ID e re
     */
    public void setId(Long id) {
        this.id = id;
    }

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
     * @param name emri i ri
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Merr kodin unik të aeroportit.
     *
     * @return kodi i aeroportit
     */
    public String getCode() {
        return code;
    }

    /**
     * Vendos kodin unik të aeroportit.
     *
     * @param code kodi i ri
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Merr vendin ku ndodhet aeroporti.
     *
     * @return objekti {@code Country}
     */
    public Country getCountry() {
        return country;
    }

    /**
     * Vendos vendin ku ndodhet aeroporti.
     *
     * @param country objekti {@code Country}
     */
    public void setCountry(Country country) {
        this.country = country;
    }

    /**
     * Merr qytetin ku ndodhet aeroporti.
     *
     * @return objekti {@code City}
     */
    public City getCity() {
        return city;
    }

    /**
     * Vendos qytetin ku ndodhet aeroporti.
     *
     * @param city objekti {@code City}
     */
    public void setCity(City city) {
        this.city = city;
    }

    /**
     * Merr zonën kohore të aeroportit.
     *
     * @return timezone si tekst
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
     * Merr tenantId-in që i përket aeroportit.
     *
     * @return tenantId si tekst
     */
    public String getTenantId() {
        return tenantId;
    }

    /**
     * Vendos tenantId-in që i përket aeroportit.
     *
     * @param tenantId tenantId i ri
     */
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
