package com.mbi_re.airport_management.model;

import jakarta.persistence.*;

@Entity
@Table(name = "immigration")
public class Immigration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String personName;
    private String nationality;
    private String entryDate;

    // Getters & setters
}
