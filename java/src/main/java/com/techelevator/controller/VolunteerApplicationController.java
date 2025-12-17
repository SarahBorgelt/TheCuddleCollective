package com.techelevator.controller;

import com.techelevator.dao.ApplicantDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.Applicant;
import com.techelevator.model.User;
import com.techelevator.model.VolunteerCodeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@RestController
@CrossOrigin
public class VolunteerApplicationController {

    private final ApplicantDao applicantDao;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public VolunteerApplicationController(ApplicantDao applicantDao, JdbcTemplate jdbcTemplate) {
        this.applicantDao = applicantDao;
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostMapping("/volunteer/apply")
    public Applicant submitApplication(@RequestBody Applicant applicant){

        return applicantDao.submitApplication(applicant);
    }

    @PostMapping("/register/volunteer-code")
    public ResponseEntity<String> applyVolunteerCode(@RequestBody VolunteerCodeDto dto, Principal principal) {

        String username = principal.getName();

        Integer userId = jdbcTemplate.queryForObject(
                "SELECT user_id FROM users WHERE username = ?",
                Integer.class,
                username
        );
        String email = jdbcTemplate.queryForObject(
                "SELECT email FROM users WHERE user_id = ?",
                String.class,
                userId
        );
        boolean success = applicantDao.applyInviteCode(
                email,
                userId,
                dto.getCode()
        );
        if (!success) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Invalid or already-used volunteer code"
            );
        }
        return ResponseEntity.ok("Volunteer code accepted");
    }

}
