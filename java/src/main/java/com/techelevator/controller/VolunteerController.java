package com.techelevator.controller;

import com.techelevator.dao.VolunteerDao;
import com.techelevator.model.ShelterVolunteer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@PreAuthorize("isAuthenticated()")
public class VolunteerController {

    private final VolunteerDao volunteerDao;

    @Autowired
    public VolunteerController(VolunteerDao volunteerDao){
        this.volunteerDao = volunteerDao;
    }

    @GetMapping("/volunteer/directory")
    @PreAuthorize("hasRole('user')") //TODO review this, I'm not entirely sure it's correct?
    public List<ShelterVolunteer> listAllVolunteers(){
        return volunteerDao.getAllVolunteers();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/volunteers")
    public ShelterVolunteer addVolunteer(@RequestBody ShelterVolunteer v) {
        return volunteerDao.createVolunteer(v);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/volunteers/{id}")
    public void deleteVolunteer(@PathVariable int id) {
        volunteerDao.deleteVolunteer(id);
    }
}
