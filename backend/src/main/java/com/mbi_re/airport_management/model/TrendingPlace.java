package com.mbi_re.airport_management.model;

import jakarta.persistence.*;

/**
 * Entiteti {@code TrendingPlace} përfaqëson një destinacion të njohur ose të rekomanduar
 * që është i rëndësishëm për sezonin aktual apo interesat e udhëtarëve.
 * Ky entitet është pjesë e funksionaliteteve të sugjerimit të destinacioneve.
 */
@Entity
@Table(name = "trending_places")
public class TrendingPlace {

    /** ID unike e vendit të rekomanduar, e gjeneruar automatikisht nga databaza */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Emri i vendit të rekomanduar (p.sh., "Paris", "Maldivet") */
    private String name;

    /** Përshkrimi i shkurtër i vendit */
    private String description;

    /** Sezoni ku vendi është më i përshtatshëm për të vizituar (p.sh., "summer", "winter") */
    private String season;

    /** URL e një imazhi përfaqësues të vendit */
    private String imageUrl;

    /** ID e tenantit për mbështetje në sistem multi-tenant */
    @Column(name = "tenant_id")
    private String tenantId;

    /**
     * Konstruktori i zbrazët i nevojshëm për JPA.
     */
    public TrendingPlace() {}

    /**
     * Konstruktori me parametra për të krijuar një objekt {@code TrendingPlace}.
     *
     * @param id ID e vendit
     * @param name emri i vendit
     * @param description përshkrimi i vendit
     * @param season sezoni i përshtatshëm
     * @param imageUrl URL e imazhit
     * @param tenantId ID e tenantit
     */
    public TrendingPlace(Long id, String name, String description, String season, String imageUrl, String tenantId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.season = season;
        this.imageUrl = imageUrl;
        this.tenantId = tenantId;
    }

    // Getters dhe Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
