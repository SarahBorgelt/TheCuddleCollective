package com.techelevator.dao;

import com.techelevator.model.Applicant;
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

    private String generateInviteCode(){
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ23456789";
        SecureRandom random = new SecureRandom();
        StringBuilder code = new StringBuilder();

        for(int i = 0;  i < 8; i++){
            code.append(chars.charAt(random.nextInt(chars.length())));
        }

        return code.toString();
    }

    public boolean applyVolunteerCode(int userId, String inviteCode){
        String sql = "SELECT volunteer_application_id FROM volunteer_applications WHERE invite_code = ? AND volunteer_application_status = 'approved' AND code_used = false";

        Integer applicationId;

        try{
            applicationId = jdbcTemplate.queryForObject(sql, Integer.class, inviteCode);
        } catch (Exception e) {
            return false;
        }

        String updateUserSql = "UPDATE users SET role = 'VOLUNTEER', first_login = FALSE WHERE user_id = ?";

        jdbcTemplate.update(updateUserSql, userId);

        String markUsedSql = "UPDATE volunteer_applications SET code_used = TRUE WHERE volunteer_application_id = ?";

        jdbcTemplate.update(markUsedSql, applicationId);

        return true;
    }

    @Override
    public boolean applyInviteCode(String email, int userId, String inviteCode) {

        String sql = "SELECT volunteer_application_id FROM volunteer_applications WHERE invite_code = ? AND email = ? AND volunteer_application_status = 'approved' AND code_used = false";

        Integer applicationId;
        try {
            applicationId = jdbcTemplate.queryForObject(
                    sql, Integer.class, inviteCode, email
            );
        } catch (Exception e) {
            return false;
        }

        jdbcTemplate.update("UPDATE users SET role = 'ROLE_VOLUNTEER', first_login = true WHERE user_id = ?", userId);

        jdbcTemplate.update("UPDATE volunteer_applications SET code_used = true WHERE volunteer_application_id = ?", applicationId);

        return true;
    }
}
