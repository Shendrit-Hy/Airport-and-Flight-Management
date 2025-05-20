package com.mbi_re.airport_management.model;

import jakarta.persistence.*;

@Entity
@Table(name = "gates")
public class Gate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String gateNumber;

    // Nëse do e lidhim me Flight, përdor ManyToOne, por mund ta lëmë thjesht për fillim
    // @ManyToOne
    // @JoinColumn(name = "flight_id")
    // private Flight flight;

    private String tenantId; // Nëse ke multi-tenant

    // Getters dhe Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGateNumber() {
        return gateNumber;
    }

    public void setGateNumber(String gateNumber) {
        this.gateNumber = gateNumber;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
