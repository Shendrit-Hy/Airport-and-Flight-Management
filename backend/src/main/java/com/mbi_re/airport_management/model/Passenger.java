package com.mbi_re.airport_management.model;

import jakarta.persistence.*;

@Entity
@Table(name = "passengers")
public class Passenger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private String email;
    private String phone;
    private String nationality;

    @Column(name = "tenant_id")
    private String tenantId;

    // Constructors
    public Passenger() {}

    public Passenger(Long id, String fullName, String email, String phone, String nationality, String tenantId) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.nationality = nationality;
        this.tenantId = tenantId;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
