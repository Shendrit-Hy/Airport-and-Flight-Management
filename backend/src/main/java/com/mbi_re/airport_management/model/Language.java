package com.mbi_re.airport_management.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "languages")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Language {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;  // "en", "sq", "de"
    private String name;  // "English", "Shqip", "Deutsch"

    @Column(name = "tenant_id", nullable = false)
    private String tenantId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
