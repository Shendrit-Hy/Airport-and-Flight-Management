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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOfficerName() {
        return officerName;
    }

    public void setOfficerName(String officerName) {
        this.officerName = officerName;
    }

    public String getItemChecked() {
        return itemChecked;
    }

    public void setItemChecked(String itemChecked) {
        this.itemChecked = itemChecked;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
