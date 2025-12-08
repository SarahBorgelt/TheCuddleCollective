package com.techelevator.controller;

import com.techelevator.dao.VolunteerDao;
import com.techelevator.model.ShelterVolunteer;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public class VolunteerController {

    private final VolunteerDao volunteerDao;

    public VolunteerController(VolunteerDao volunteerDao){
        this.volunteerDao = volunteerDao;
    }

    @GetMapping("/volunteers")
    public List<ShelterVolunteer> listAllVolunteers(){
        return volunteerDao.getAllVolunteers();
    }
}
