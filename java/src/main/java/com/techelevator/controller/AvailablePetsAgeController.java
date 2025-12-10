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
@RequestMapping( path = "/availablePets/petAge/")
public class AvailablePetsAgeController {

    private final AvailablePetDao availablePetDao;

    @Autowired
    public AvailablePetsAgeController(AvailablePetDao availablePetDao) {
        this.availablePetDao = availablePetDao;
    }

    @GetMapping(path = "{age}")
    public List<AvailablePet> availablePetsByAge(@PathVariable Integer petAge){
        try{
            return availablePetDao.getAvailablePetsByAge(petAge);
        } catch(DaoException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
            }
        }
    }
