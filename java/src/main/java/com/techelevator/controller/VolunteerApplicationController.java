package com.techelevator.controller;

import com.techelevator.dao.ApplicantDao;
import com.techelevator.model.Applicant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class VolunteerApplicationController {

    private final ApplicantDao applicantDao;

    @Autowired
    public VolunteerApplicationController(ApplicantDao applicantDao) {
        this.applicantDao = applicantDao;
    }

    @PostMapping("/volunteer/apply")
    public Applicant submitApplication(@RequestBody Applicant applicant){

        return applicantDao.submitApplication(applicant);
    }

}
