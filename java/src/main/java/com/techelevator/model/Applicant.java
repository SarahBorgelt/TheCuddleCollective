package com.techelevator.model;

import jakarta.validation.constraints.NotBlank;

public class Applicant extends ShelterApplication {
//    volunteer_application_id SERIAL PRIMARY KEY,
//    volunteer_id INT REFERENCES volunteers(volunteer_id),
//    first_name VARCHAR(100) NOT NULL,
//    last_name VARCHAR(100) NOT NULL,
//    email VARCHAR(150) NOT NULL UNIQUE,
    private Long volunteerApplicationId;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String email;
    @NotBlank
    private String phoneNumber;

    public Applicant(){

    }

    public Applicant(String firstName, String lastName, String email, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public Long getVolunteerApplicationId() {
        return volunteerApplicationId;
    }

    public void setVolunteerApplicationId(Long volunteerApplicationId) {
        this.volunteerApplicationId = volunteerApplicationId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
