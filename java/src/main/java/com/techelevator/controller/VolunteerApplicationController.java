package com.techelevator.controller;

import com.techelevator.dao.ApplicantDao;
import com.techelevator.model.Applicant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@PreAuthorize("isAuthenticated()")
public class VolunteerApplicationController {

    private final ApplicantDao applicantDao;

    @Autowired
    public VolunteerApplicationController(ApplicantDao applicantDao) {
        this.applicantDao = applicantDao;
    }

    @PostMapping("volunteer/submit")
    public Applicant submitApplication(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String email, @RequestParam String phoneNumber){

        Applicant applicant = new Applicant(firstName, lastName, email, phoneNumber);

        return applicantDao.submitApplication(applicant);
    }

}
