package com.techelevator.controller;

import com.techelevator.dao.VolunteerDao;
import com.techelevator.model.ShelterVolunteer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@PreAuthorize("hasAnyRole('ADMIN', 'VOLUNTEER')")
public class VolunteerController {

    private final VolunteerDao volunteerDao;

    @Autowired
    public VolunteerController(VolunteerDao volunteerDao){
        this.volunteerDao = volunteerDao;
    }

    @GetMapping("/volunteer/directory")
    @PreAuthorize("hasAnyRole('ADMIN', 'VOLUNTEER')")
    public List<ShelterVolunteer> listAllVolunteers(){
        return volunteerDao.getAllVolunteers();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'VOLUNTEER')")
    @PostMapping("/volunteers")
    public ShelterVolunteer addVolunteer(@RequestBody ShelterVolunteer v) {
        return volunteerDao.createVolunteer(v);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'VOLUNTEER')")
    @DeleteMapping("/volunteers/{id}")
    public void deleteVolunteer(@PathVariable int id) {
        volunteerDao.deleteVolunteer(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'VOLUNTEER')")
    @PutMapping("/volunteers/{id}")
    public ShelterVolunteer updateVolunteer(@PathVariable String id, @RequestBody ShelterVolunteer volunteer) {
        volunteer.setVolunteerId(id);
        return volunteerDao.update(volunteer);
    }
}
