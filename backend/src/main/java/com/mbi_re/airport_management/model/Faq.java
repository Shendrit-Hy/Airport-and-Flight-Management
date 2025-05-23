package com.mbi_re.airport_management.model;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Entiteti {@code Faq} përfaqëson një pyetje dhe përgjigje të zakonshme (Frequently Asked Question)
 * në sistemin e menaxhimit të aeroporteve.
 * Ky entitet lidhet me një tenant të caktuar dhe përdoret për të ndihmuar përdoruesit me informacione të dobishme.
 */
@Entity
@Table(name = "faqs")
@Data
public class Faq {

    /** ID unike e pyetjes së faq-it, e gjeneruar automatikisht */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Pyetja e shfaqur në seksionin e FAQ-ve */
    private String question;

    /** Përgjigja që i korrespondon pyetjes */
    private String answer;

    /** ID e tenantit për të mbështetur multi-tenancy */
    @Column(nullable = false)
    private String tenantId;

    /**
     * Merr ID-në e FAQ-ut.
     *
     * @return ID si {@code Long}
     */
    public Long getId() {
        return id;
    }

    /**
     * Vendos ID-në e FAQ-ut.
     *
     * @param id ID e re
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Merr pyetjen e shfaqur.
     *
     * @return pyetja si tekst
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Vendos pyetjen e re.
     *
     * @param question pyetja e re
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     * Merr përgjigjen për pyetjen.
     *
     * @return përgjigja si tekst
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * Vendos përgjigjen për pyetjen.
     *
     * @param answer përgjigja e re
     */
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    /**
     * Merr tenantId-in për këtë njësi FAQ.
     *
     * @return tenantId si tekst
     */
    public String getTenantId() {
        return tenantId;
    }

    /**
     * Vendos tenantId-in për këtë njësi FAQ.
     *
     * @param tenantId tenantId i ri
     */
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
