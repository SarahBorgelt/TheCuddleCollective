package com.techelevator.controller;

import com.techelevator.dao.ApplicantDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.Applicant;
import com.techelevator.model.User;
import com.techelevator.model.VolunteerCodeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@CrossOrigin
public class VolunteerApplicationController {

    private final ApplicantDao applicantDao;
    private final UserDao userDao;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public VolunteerApplicationController(ApplicantDao applicantDao, UserDao userDao, JdbcTemplate jdbcTemplate) {
        this.applicantDao = applicantDao;
        this.userDao = userDao;
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostMapping("/volunteer/apply")
    public Applicant submitApplication(@RequestBody Applicant applicant){

        return applicantDao.submitApplication(applicant);
    }

    @PostMapping("/register/volunteer-code")
    public void applyVolunteerCode(@RequestBody VolunteerCodeDto dto,
                                   Principal principal) {

        Integer userId = jdbcTemplate.queryForObject(
                "SELECT user_id FROM users WHERE username = ?", Integer.class, principal.getName()
        );
    }

}
