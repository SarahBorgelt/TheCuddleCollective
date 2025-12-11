package com.techelevator.model;

import jakarta.validation.constraints.NotBlank;

public class Parent {

private long parentId;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String phoneNumber;
    @NotBlank
    private int streetNumber;
    @NotBlank
    private String streetName;
    @NotBlank
    private String cityName;
    @NotBlank
    private String stateAbbreviation;

    public Parent(){

    };

    public Parent(long parentId, String firstName, String lastName,
                  String phoneNumber, int streetNumber, String streetName,
                  String cityName, String stateAbbreviation) {
        this.parentId = parentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.streetNumber = streetNumber;
        this.streetName = streetName;
        this.cityName = cityName;
        this.stateAbbreviation = stateAbbreviation;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public long getParentId() {
        return parentId;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(int streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getStateAbbreviation() {
        return stateAbbreviation;
    }

    public void setStateAbbreviation(String stateAbbreviation) {
        this.stateAbbreviation = stateAbbreviation;
    }
}
