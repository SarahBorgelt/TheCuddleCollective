package com.techelevator.dao;

import com.techelevator.model.Applicant;
import org.springframework.jdbc.core.JdbcTemplate;

public class JdbcApplicantDao implements ApplicantDao {
    private final JdbcTemplate jdbcTemplate;

    public JdbcApplicantDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Applicant submitApplication(Applicant applicant) {
        String sql =
                "INSERT INTO volunteer_applications (volunteer_id, first_name, last_name, email) " +
                        "VALUES (?, ?, ?, ?) RETURNING volunteer_application_id;";

        Integer newId = jdbcTemplate.queryForObject(
                sql,
                Integer.class,
                applicant.getVolunteerId(),
                applicant.getFirstName(),
                applicant.getLastName(),
                applicant.getEmail()
        );

        applicant.setVolunteerApplicationId(newId);
        return applicant;
    }
}
