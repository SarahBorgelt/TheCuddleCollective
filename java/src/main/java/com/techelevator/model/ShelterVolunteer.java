package com.techelevator.model;

import jakarta.validation.constraints.NotBlank;

public class ShelterVolunteer {
    @NotBlank
    private String volunteerId;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String email;
    @NotBlank
    private String phoneNumber;

    private boolean firstLogin;
    private boolean temporaryPasswordActive;
    private String username;
    private String passwordHash;

    public ShelterVolunteer(){

    }

    public ShelterVolunteer(String volunteerId, String firstName, String lastName, String email, String phoneNumber, boolean firstLogin, boolean temporaryPasswordActive){
        this.volunteerId = volunteerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.firstLogin = firstLogin;
        this.temporaryPasswordActive = temporaryPasswordActive;
    }

    public String getVolunteerId() {
        return volunteerId;
    }

    public void setVolunteerId(String volunteerId) {
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isTemporaryPasswordActive() {
        return temporaryPasswordActive;
    }

    public void setTemporaryPasswordActive(boolean temporaryPasswordActive) {
        this.temporaryPasswordActive = temporaryPasswordActive;
    }

    public boolean isFirstLogin() {
        return firstLogin;
    }

    public void setFirstLogin(boolean firstLogin) {
        this.firstLogin = firstLogin;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash){
        this.passwordHash = passwordHash;
    }
}
