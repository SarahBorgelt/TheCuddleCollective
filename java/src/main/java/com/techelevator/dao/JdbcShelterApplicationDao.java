package com.techelevator.dao;

import com.techelevator.model.ShelterApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcShelterApplicationDao implements ShelterApplicationDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcShelterApplicationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public ShelterApplication findById(int applicationId) {
        String sql = "SELECT volunteer_application_id, first_name, last_name, email " +
                "FROM volunteer_applications WHERE volunteer_application_id = ?";

        SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, applicationId);

        if (rs.next()) {
            return mapRowToApplication(rs);
        }
        return null;
    }

    @Override
    public List<ShelterApplication> findAllPending() {
        String sql = "SELECT volunteer_application_id, first_name, last_name, email, phone_number, volunteer_application_status " +
                "FROM volunteer_applications WHERE volunteer_application_status = 'pending'";;

        List<ShelterApplication> apps = new ArrayList<>();
        SqlRowSet rs = jdbcTemplate.queryForRowSet(sql);

        while (rs.next()) {
            apps.add(mapRowToApplication(rs));
        }
        return apps;
    }

    public String getApplicationStatus(int applicationId) {
        ShelterApplication getByStatus = new ShelterApplication();
        String returnStatus = "";
        String sql = "SELECT volunteer_application_status " +
                "FROM volunteer_applications WHERE volunteer_application_id = ?";

        SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, applicationId);

        if (rs.next()) {
            returnStatus = rs.getString("volunteer_application_status");
        } return returnStatus;
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM volunteer_applications WHERE volunteer_application_id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public void save(ShelterApplication app) {
        String sql = """
            INSERT INTO volunteer_applications 
            (first_name, last_name, email, phone_number, volunteer_application_status)
            VALUES (?, ?, ?, ?, 'pending')
            RETURNING volunteer_application_id
            """;

        Integer newId = jdbcTemplate.queryForObject(sql, Integer.class,
                app.getFirstName(),
                app.getLastName(),
                app.getEmail(),
                app.getPhoneNumber()
        );

        app.setApplicationId(newId);
        app.setStatus("pending");
    }



    private ShelterApplication mapRowToApplication(SqlRowSet rs) {
        ShelterApplication app = new ShelterApplication();
        app.setApplicationId(rs.getInt("volunteer_application_id"));
        app.setFirstName(rs.getString("first_name"));
        app.setLastName(rs.getString("last_name"));
        app.setEmail(rs.getString("email"));
        return app;
    }
}
