package com.techelevator.controller;

import com.techelevator.dao.AvailablePetDao;
import com.techelevator.exception.DaoException;
import com.techelevator.model.AvailablePet;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@CrossOrigin
@RequestMapping( path = "/availablePets/id/")
public class AvailablePetsIdController {

    private final AvailablePetDao availablePetDao;

    public AvailablePetsIdController(AvailablePetDao availablePetDao) {
        this.availablePetDao = availablePetDao;
    }

    @GetMapping(path = "{id}")
    public AvailablePet getAvailablePetById(@PathVariable long petId){
        try{
            return availablePetDao.getPetById(petId);
        } catch (DaoException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
