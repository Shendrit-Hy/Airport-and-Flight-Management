package com.mbi_re.airport_management.dto;

import jakarta.validation.constraints.NotBlank;

public class LanguageDTO {

    @NotBlank(message = "Language name is required")
    private String name;

    @NotBlank(message = "Language code is required")
    private String code;

    public LanguageDTO() {
    }

    public LanguageDTO(String name, String code) {
        this.name = name;
        this.code = code;
    }

    // Getters dhe Setters manual
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
