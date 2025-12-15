package com.techelevator.controller;

import com.techelevator.dao.AvailablePetDao;
import com.techelevator.exception.DaoException;
import com.techelevator.model.AvailablePet;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/adoptedPets")
public class AdoptedPetsController {

    private final AvailablePetDao availablePetDao;

    @Autowired
    public AdoptedPetsController(AvailablePetDao availablePetDao){
        this.availablePetDao = availablePetDao;
    }

    @GetMapping
    @PermitAll
    public List<AvailablePet> getAllAdoptedPets(){
        try{
            return availablePetDao.getAllAdoptedPets();
        } catch (DaoException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    // Use an ID GET method to grab a pet from the list and then "display" it on the page?
}
