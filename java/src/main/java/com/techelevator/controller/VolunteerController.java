package com.techelevator.controller;

import com.techelevator.dao.VolunteerDao;
import com.techelevator.model.ShelterVolunteer;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@PreAuthorize("isAuthenticated()")
public class VolunteerController {

    private final VolunteerDao volunteerDao;

    public VolunteerController(VolunteerDao volunteerDao){
        this.volunteerDao = volunteerDao;
    }

    @GetMapping("/volunteer/directory")
    public List<ShelterVolunteer> listAllVolunteers(){
        return volunteerDao.getAllVolunteers();
    }
}
