package com.techelevator.controller;

import com.techelevator.model.Applicant;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@PreAuthorize("isAuthenticated()")
public class VolunteerApplicationController {

    @PostMapping("/submit")
    public void submitApplication(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String email){

        Applicant applicant = new Applicant(firstName, lastName, email);

    }

}
