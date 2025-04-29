package com.mbi_re.airport_management.model;

import jakarta.persistence.*;

@Entity
@Table(name = "shifts")
public class Shift {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String shiftName;
    private String startTime;
    private String endTime;

    // Getters & setters
}
