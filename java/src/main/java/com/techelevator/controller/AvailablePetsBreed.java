package com.techelevator.controller;

import com.techelevator.dao.AvailablePetDao;
import com.techelevator.exception.DaoException;
import com.techelevator.model.AvailablePet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping( path = "/availablePets/breed/")
public class AvailablePetsBreed {

    private final AvailablePetDao availablePetDao;

    @Autowired
    public AvailablePetsBreed(AvailablePetDao availablePetDao) {
        this.availablePetDao = availablePetDao;
    }

    @GetMapping("/{breed}")
    public List<AvailablePet> availablePetsByBreed(@PathVariable String breed){
        try{
            return availablePetDao.getAvailablePetsByBreed(breed);
        } catch (DaoException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
