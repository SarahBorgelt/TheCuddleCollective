package com.techelevator.dao;

import com.techelevator.model.Applicant;
import com.techelevator.model.ShelterApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class JdbcApplicantDao implements ApplicantDao {
    private final JdbcTemplate jdbcTemplate;

    public JdbcApplicantDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Applicant submitApplication(Applicant applicant) {
        String sql =
                "INSERT INTO volunteer_applications (first_name, last_name, email, phone_number, volunteer_application_status) " +
                        "VALUES (?, ?, ?, ?, 'pending') RETURNING volunteer_application_id";

        Integer newId = jdbcTemplate.queryForObject(
                sql,
                Integer.class,
                applicant.getFirstName(),
                applicant.getLastName(),
                applicant.getEmail(),
                applicant.getPhoneNumber()
        );

        applicant.setApplicationId(newId);
        applicant.setStatus("pending");
        return applicant;
    }
}
