package com.mbi_re.airport_management.dto;

import lombok.Data;

@Data
public class LostItemDTO {
    private String itemName;
    private String description;
    private String location;
    private String contactEmail;
}
