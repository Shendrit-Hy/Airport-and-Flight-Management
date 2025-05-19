package com.mbi_re.airport_management.dto;

public class PassengerDTO {
    private String fullName;
    private String email;
    private String phone;
    private String nationality;

    public PassengerDTO() {}

    public PassengerDTO(String fullName, String email, String phone, String nationality) {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.nationality = nationality;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }
}