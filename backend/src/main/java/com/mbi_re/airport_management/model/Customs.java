package com.mbi_re.airport_management.model;

import jakarta.persistence.*;

@Entity
@Table(name = "customs")
public class Customs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String officerName;
    private String itemChecked;
    private String date;

    // Getters & setters
}
