package com.techelevator.controller;

import com.techelevator.dao.AvailablePetDao;
import com.techelevator.exception.DaoException;
import com.techelevator.model.AvailablePet;
import com.techelevator.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping( path = "/availablePets/type/")
public class AvailablePetsTypeController {
    private final AvailablePetDao availablePetDao;

    public AvailablePetsTypeController(AvailablePetDao availablePetDao) {
        this.availablePetDao = availablePetDao;
    }

    @GetMapping(path = "{type}")
    public List <AvailablePet> getAvailablePetsByType(@PathVariable String type){
        try{
            return availablePetDao.getAvailablePetByType(type);
        } catch (DaoException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
