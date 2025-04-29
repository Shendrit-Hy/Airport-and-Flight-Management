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
}
