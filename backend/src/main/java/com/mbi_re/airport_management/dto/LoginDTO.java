package com.mbi_re.airport_management.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO (Data Transfer Object) që përdoret për procesin e autentikimit.
 * Përmban kredencialet e përdoruesit që nevojiten gjatë login-it në sistem.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {

    /** Emri i përdoruesit (username) i përdorur për kyçje */
    private String username;

    /** Fjalëkalimi i përdoruesit */
    private String password;

    /**
     * Merr emrin e përdoruesit.
     *
     * @return emri i përdoruesit
     */
    public String getUsername() {
        return username;
    }

    /**
     * Vendos emrin e përdoruesit.
     *
     * @param username emri i ri i përdoruesit
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Merr fjalëkalimin e përdoruesit.
     *
     * @return fjalëkalimi
     */
    public String getPassword() {
        return password;
    }

    /**
     * Vendos fjalëkalimin e përdoruesit.
     *
     * @param password fjalëkalimi i ri
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
