package com.mbi_re.airport_management.dto;

import lombok.Data;

@Data
public class TimeZoneDTO {
    private String id;      // e.g., "Asia/Kolkata"
    private String offset;  // e.g., "UTC+05:30"

    public TimeZoneDTO() {}

    public TimeZoneDTO(String id, String offset) {
        this.id = id;
        this.offset = offset;
    }

    // Getters & Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOffset() {
        return offset;
    }

    public void setOffset(String offset) {
        this.offset = offset;
    }
}

