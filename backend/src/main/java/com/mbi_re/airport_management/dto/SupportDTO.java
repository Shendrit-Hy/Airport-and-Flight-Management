package com.mbi_re.airport_management.dto;
import lombok.Data;

@Data
public class SupportDTO {
    public String subject;
    public String message;
    public String email;
    private String tenantId;
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    // Getter dhe Setter për message
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    // Getter dhe Setter për email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Getter dhe Setter për tenantId
    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
