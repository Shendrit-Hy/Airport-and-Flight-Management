// TrendingPlaceDTO.java
package com.mbi_re.airport_management.dto;

import lombok.*;

/**
 * DTO (Data Transfer Object) për përfaqësimin e një destinacioni të njohur (Trending Place).
 * Kjo klasë përdoret për të shfaqur vende të rekomanduara bazuar në sezonin apo interesin turistik.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrendingPlaceDTO {

    /** Emri i vendit të rekomanduar (p.sh., Paris, Bali) */
    private String name;

    /** Përshkrimi i shkurtër i vendit */
    private String description;

    /** Sezoni ku ky vend është më i përshtatshëm për të vizituar (p.sh., Summer, Winter) */
    private String season;

    /** URL e një imazhi përfaqësues për këtë destinacion */
    private String imageUrl;

    /**
     * Merr emrin e vendit të rekomanduar.
     *
     * @return emri i vendit
     */
    public String getName() {
        return name;
    }

    /**
     * Vendos emrin e vendit të rekomanduar.
     *
     * @param name emri i ri i vendit
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Merr përshkrimin e vendit të rekomanduar.
     *
     * @return përshkrimi i vendit
     */
    public String getDescription() {
        return description;
    }

    /**
     * Vendos përshkrimin e vendit të rekomanduar.
     *
     * @param description përshkrimi i ri
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Merr sezonin përkatës për të vizituar këtë vend.
     *
     * @return sezoni si tekst
     */
    public String getSeason() {
        return season;
    }

    /**
     * Vendos sezonin përkatës për këtë vend.
     *
     * @param season sezoni i ri
     */
    public void setSeason(String season) {
        this.season = season;
    }

    /**
     * Merr URL-në e imazhit të vendit të rekomanduar.
     *
     * @return URL e imazhit
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * Vendos URL-në e imazhit të vendit të rekomanduar.
     *
     * @param imageUrl URL e re e imazhit
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
