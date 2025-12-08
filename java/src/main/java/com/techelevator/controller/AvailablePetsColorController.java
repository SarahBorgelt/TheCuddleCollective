package com.techelevator.controller;

import com.techelevator.dao.AvailablePetDao;
import com.techelevator.exception.DaoException;
import com.techelevator.model.AvailablePet;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping( path = "/availablePets/color/")
public class AvailablePetsColorController {

    private final AvailablePetDao availablePetDao;


    public AvailablePetsColorController(AvailablePetDao availablePetDao) {
        this.availablePetDao = availablePetDao;
    }

    @GetMapping(path = "{color}")
    public List<AvailablePet> availablePetsByColor(@PathVariable String color){
        try{
            return availablePetDao.getAvailablePetByColor(color);
        } catch (DaoException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
