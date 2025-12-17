package com.techelevator.model;

import jakarta.validation.constraints.NotBlank;

public class Applicant extends ShelterApplication {
    private int volunteerApplicationId;

    public Applicant(){

    }

    public Applicant(int volunteerApplicationId) {
        this.volunteerApplicationId = volunteerApplicationId;
    }

    public int getVolunteerApplicationId() {
        return volunteerApplicationId;
    }

    public void setVolunteerApplicationId(int volunteerApplicationId) {
        this.volunteerApplicationId = volunteerApplicationId;
    }
}
