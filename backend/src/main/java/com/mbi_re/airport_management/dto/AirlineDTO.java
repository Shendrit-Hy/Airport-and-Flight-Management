package com.mbi_re.airport_management.dto;

public class AirlineDTO {
    private Long id;
    private String name;

    public AirlineDTO() {}

    public AirlineDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

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
}
