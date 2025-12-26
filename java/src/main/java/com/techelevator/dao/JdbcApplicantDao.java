package com.techelevator.dao;

import com.techelevator.model.Applicant;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.security.SecureRandom;

@Component
public class JdbcApplicantDao implements ApplicantDao {
    private final JdbcTemplate jdbcTemplate;

    public JdbcApplicantDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Applicant submitApplication(Applicant applicant) {

        String existsSql = "SELECT COUNT(*) FROM volunteer_applications WHERE email = ? AND volunteer_application_status IN ('pending', 'approved')";

        Integer count = jdbcTemplate.queryForObject(
                existsSql,
                Integer.class,
                applicant.getEmail()
        );

        if (count != null && count > 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "An application already exists for this email"
            );
        }
        String inviteCode = generateInviteCode();

        String sql =
                "INSERT INTO volunteer_applications(first_name, last_name, email, phone_number, volunteer_application_status, invite_code) " +
                        "VALUES (?, ?, ?, ?, 'pending', ?) RETURNING volunteer_application_id";

        Integer newId = jdbcTemplate.queryForObject(
                sql,
                Integer.class,
                applicant.getFirstName(),
                applicant.getLastName(),
                applicant.getEmail(),
                applicant.getPhoneNumber(),
                inviteCode
        );

        applicant.setApplicationId(newId);
        applicant.setStatus("pending");
        applicant.setInviteCode(inviteCode);

        return applicant;
    }

    private String generateInviteCode() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ23456789";
        SecureRandom random = new SecureRandom();
        StringBuilder code = new StringBuilder();

        for (int i = 0; i < 8; i++) {
            code.append(chars.charAt(random.nextInt(chars.length())));
        }

        return code.toString();
    }

    public boolean applyInviteCode(String username, String inviteCode) {

        Integer userId;
        try {
            userId = jdbcTemplate.queryForObject(
                    "SELECT user_id FROM users WHERE username = ?",
                    Integer.class,
                    username
            );
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
        Integer applicationId;
        try {
            applicationId = jdbcTemplate.queryForObject(
                    "SELECT volunteer_application_id " +
                            "FROM volunteer_applications " +
                            "WHERE invite_code = ? " +
                            "AND volunteer_application_status = 'approved' " +
                            "AND code_used = false",
                    Integer.class,
                    inviteCode
            );
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
        jdbcTemplate.update(
                "UPDATE volunteer_applications SET code_used = true WHERE volunteer_application_id = ?",
                applicationId
        );
        jdbcTemplate.update(
                "UPDATE users SET role = 'ROLE_VOLUNTEER', first_login = false WHERE user_id = ?",
                userId
        );

        return true;
    }
}
