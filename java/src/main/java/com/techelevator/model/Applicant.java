package com.techelevator.model;

import jakarta.validation.constraints.NotBlank;

public class Applicant {
//    volunteer_application_id SERIAL PRIMARY KEY,
//    volunteer_id INT REFERENCES volunteers(volunteer_id),
//    first_name VARCHAR(100) NOT NULL,
//    last_name VARCHAR(100) NOT NULL,
//    email VARCHAR(150) NOT NULL UNIQUE,
    private int volunteerApplicationId;
    private int volunteerId;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String email;

    public Applicant(){

    }

    public Applicant(String firstName, String lastName, String email) {

    }

    public int getVolunteerApplicationId() {
        return volunteerApplicationId;
    }

    public void setVolunteerApplicationId(int volunteerApplicationId) {
        this.volunteerApplicationId = volunteerApplicationId;
    }

    public int getVolunteerId() {
        return volunteerId;
    }

    public void setVolunteerId(int volunteerId) {
        this.volunteerId = volunteerId;
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
}
