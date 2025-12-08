package com.techelevator.dao;

import com.techelevator.model.AvailablePet;

import java.util.List;

public interface AvailablePetDao {

    AvailablePet getAvailablePetByBreed(String breed);

    AvailablePet getAvailablePetByColor(String Color);

    AvailablePet getAvailablePetByAge(Integer age);

    AvailablePet getAvailablePetByType(String type);

    AvailablePet getAvailablePetByAdoptionStatus(String adoptionStatus);

    AvailablePet getAvailablePetByBreed(String breed);

    AvailablePet getAvailablePetByColor(String color);

    AvailablePet getAvailablePetByAge(Integer age);

    AvailablePet getAvailablePetByType(String type);

    AvailablePet getAvailablePetByAdoptionStatus(String adoptionStatus);

    void addPet(AvailablePet pet);

    void updatePet(AvailablePet pet);

    List<AvailablePet> getAllPets();

}
