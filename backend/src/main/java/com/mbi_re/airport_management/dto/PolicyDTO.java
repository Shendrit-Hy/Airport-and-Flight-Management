package com.mbi_re.airport_management.dto;

import lombok.Data;

/**
 * DTO (Data Transfer Object) për përfaqësimin e një politike (policy) të sistemit,
 * si p.sh. rregullore, kushte përdorimi ose politika privatësie për secilin tenant.
 */
@Data
public class PolicyDTO {

    /** ID e tenantit që lidhet me këtë politikë */
    private String tenantId;

    /** Titulli i politikës (p.sh., "Politika e Privatësisë") */
    private String title;

    /** Përmbajtja e plotë e politikës në formë teksti */
    private String content;

    /** Lloji i politikës (p.sh., "privacy", "terms", "cancellation") */
    private String type;

    /**
     * Merr tenantId-in që lidhet me këtë politikë.
     *
     * @return tenantId si tekst
     */
    public String getTenantId() {
        return tenantId;
    }

    /**
     * Vendos tenantId-in për këtë politikë.
     *
     * @param tenantId tenantId i ri
     */
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    /**
     * Merr titullin e politikës.
     *
     * @return titulli si tekst
     */
    public String getTitle() {
        return title;
    }

    /**
     * Vendos titullin e politikës.
     *
     * @param title titulli i ri
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Merr përmbajtjen e politikës.
     *
     * @return përmbajtja si tekst
     */
    public String getContent() {
        return content;
    }

    /**
     * Vendos përmbajtjen e politikës.
     *
     * @param content përmbajtja e re
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Merr llojin e politikës.
     *
     * @return tipi i politikës
     */
    public String getType() {
        return type;
    }

    /**
     * Vendos llojin e politikës.
     *
     * @param type tipi i ri i politikës
     */
    public void setType(String type) {
        this.type = type;
    }
}
