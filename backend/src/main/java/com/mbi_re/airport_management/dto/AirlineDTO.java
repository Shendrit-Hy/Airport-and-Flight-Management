package com.mbi_re.airport_management.dto;

/**
 * DTO (Data Transfer Object) për përfaqësimin e një linje ajrore.
 * Përdoret për transferimin e të dhënave midis shtresave të aplikacionit.
 */
public class AirlineDTO {
    /** ID unike e linjës ajrore */
    private Long id;

    /** Emri i linjës ajrore */
    private String name;

    /** Konstruktori i zbrazët (i nevojshëm për serializim/deserializim) */
    public AirlineDTO() {}

    /**
     * Konstruktori me parametra për inicializimin e objektit AirlineDTO.
     *
     * @param id   ID e linjës ajrore
     * @param name Emri i linjës ajrore
     */
    public AirlineDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Merr ID e linjës ajrore.
     *
     * @return ID si {@code Long}
     */
    public Long getId() {
        return id;
    }

    /**
     * Vendos ID e linjës ajrore.
     *
     * @param id ID e re për linjën ajrore
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Merr emrin e linjës ajrore.
     *
     * @return Emri si {@code String}
     */
    public String getName() {
        return name;
    }

    /**
     * Vendos emrin e linjës ajrore.
     *
     * @param name Emri i ri i linjës ajrore
     */
    public void setName(String name) {
        this.name = name;
    }
}
