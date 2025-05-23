package com.mbi_re.airport_management.dto;

import lombok.Data;

/**
 * DTO (Data Transfer Object) për përfaqësimin e një pyetjeje të shpeshtë (FAQ).
 * Përdoret për transferimin e të dhënave të FAQ-ve midis shtresave të aplikacionit.
 */
@Data
public class FaqDTO {

    /** ID unike e pyetjes së shpeshtë */
    private Long id;

    /** Pyetja që i përgjigjet ky objekt FAQ */
    private String question;

    /** Përgjigjja ndaj pyetjes */
    private String answer;

    /** ID e tenantit për të mbështetur multi-tenancy */
    private String tenantId;

    /**
     * Merr ID-në e faq-it.
     *
     * @return ID si {@code Long}
     */
    public Long getId() {
        return id;
    }

    /**
     * Vendos ID-në e faq-it.
     *
     * @param id ID e re
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Merr pyetjen.
     *
     * @return pyetja si {@code String}
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Vendos pyetjen.
     *
     * @param question pyetja e re
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     * Merr përgjigjen e pyetjes.
     *
     * @return përgjigjja si {@code String}
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * Vendos përgjigjen e pyetjes.
     *
     * @param answer përgjigjja e re
     */
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    /**
     * Merr tenantId-in që lidhet me këtë FAQ.
     *
     * @return tenantId si {@code String}
     */
    public String getTenantId() {
        return tenantId;
    }

    /**
     * Vendos tenantId-in për këtë FAQ.
     *
     * @param tenantId tenantId i ri
     */
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
