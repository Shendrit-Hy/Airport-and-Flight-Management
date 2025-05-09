package com.mbi_re.airport_management.model;

import jakarta.persistence.*;

@Entity
@Table(name = "security")
public class Security {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String guardName;
    private String assignedShift;

    // Getters & setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGuardName() {
        return guardName;
    }

    public void setGuardName(String guardName) {
        this.guardName = guardName;
    }

    public String getAssignedShift() {
        return assignedShift;
    }

    public void setAssignedShift(String assignedShift) {
        this.assignedShift = assignedShift;
    }
}
