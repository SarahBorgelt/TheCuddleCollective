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
@RequestMapping( path = "/availablePets/status/")
public class AvailablePetsAdoptionStatusController {

    private final AvailablePetDao availablePetDao;

    public AvailablePetsAdoptionStatusController(AvailablePetDao availablePetDao) {
        this.availablePetDao = availablePetDao;
    }
    @GetMapping(path = "{adoptionStatus}")
    public List<AvailablePet> availablePetsByAdoptionStatus(@PathVariable String adoptionStatus){
        try{
            return availablePetDao.getAvailablePetByAdoptionStatus(adoptionStatus);
        } catch (DaoException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
