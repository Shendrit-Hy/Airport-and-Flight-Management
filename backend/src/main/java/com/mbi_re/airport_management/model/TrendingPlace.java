package com.mbi_re.airport_management.model;

import jakarta.persistence.*;

@Entity
@Table(name = "trending_places")
public class TrendingPlace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String season;
    private String imageUrl;

    @Column(name = "tenant_id")
    private String tenantId;

    public TrendingPlace() {}

    public TrendingPlace(Long id, String name, String description, String season, String imageUrl, String tenantId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.season = season;
        this.imageUrl = imageUrl;
        this.tenantId = tenantId;
    }

    // Getter & Setter methods
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
