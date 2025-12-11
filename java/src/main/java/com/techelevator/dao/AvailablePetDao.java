package com.techelevator.dao;

import com.techelevator.model.AvailablePet;

import java.util.List;

public interface AvailablePetDao {

    List<AvailablePet> getAvailablePetsByBreed(String breed);

    List<AvailablePet> getAvailablePetsByColor(String Color);

    List<AvailablePet> getAvailablePetsByAge(Integer age);

    List<AvailablePet> getAvailablePetsByType(String type);

    List<AvailablePet> getAvailablePetsByAdoptionStatus(String adoptionStatus);

    AvailablePet getPetById(long petId);

    AvailablePet addPet(AvailablePet pet);

    AvailablePet updatePet(AvailablePet pet);

    List<AvailablePet> getAllPets();

<<<<<<< java/src/main/java/com/techelevator/dao/AvailablePetDao.java
    List<AvailablePet> getAllAdoptedPets();

    void updatePetToAdopted(AvailablePet pet, int parent_id);
=======
    void updatePetToAdopted(AvailablePet pet, int parent_id);
>>>>>>> java/src/main/java/com/techelevator/dao/AvailablePetDao.java
}
