package com.techelevator.controller;

import com.techelevator.dao.AvailablePetDao;
import com.techelevator.exception.DaoException;
import com.techelevator.model.AvailablePet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@PreAuthorize("isAuthenticated()")
@RequestMapping( path = "/availablePets")
public class AvailablePetsController {
    private AvailablePetDao availablePetDao;

    @Autowired
    public AvailablePetsController(AvailablePetDao availablePetDao){
        this.availablePetDao = availablePetDao;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<AvailablePet> getAllPets() {
        List<AvailablePet> availablePets = new ArrayList<>();
        try {
            availablePets = availablePetDao.getAllPets();
        }
        catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
        return availablePets;
    }

    @PreAuthorize("hasRole('user')")
    @PutMapping("/{id}")
    public void updateAvailablePets(@PathVariable Long id, @RequestBody AvailablePet pet){
        try{
            pet.setAnimalId(id);
            availablePetDao.updatePet(pet);
        } catch (DaoException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PreAuthorize("hasRole('user')")
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public AvailablePet addAvailablePet(@RequestBody AvailablePet availablePet){
        try{
            return availablePetDao.addPet(availablePet);
        } catch(DaoException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
