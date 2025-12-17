package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.ShelterVolunteer;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcVolunteersDao implements VolunteerDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcVolunteersDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<ShelterVolunteer> getAllVolunteers() {

        List<ShelterVolunteer> volunteers = new ArrayList<>();

        String sql = "SELECT volunteer_id, first_name, last_name, email, phone_number FROM volunteers ORDER BY last_name, first_name";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                volunteers.add(mapRowToVolunteer(results));
            }
            return volunteers;
        } catch (Exception e) {
            throw new DaoException("Cannot retrieve volunteers", e);
        }
    }

    @Override
    public ShelterVolunteer createVolunteer(ShelterVolunteer volunteer) {

        String sql = "INSERT INTO volunteers (first_name, last_name, email, phone_number) VALUES (?, ?, ?, ?) RETURNING volunteer_id";
        try {
            Integer newId = jdbcTemplate.queryForObject(
                    sql,
                    Integer.class,
                    volunteer.getFirstName(),
                    volunteer.getLastName(),
                    volunteer.getEmail(),
                    volunteer.getPhoneNumber()
            );
            volunteer.setVolunteerId(String.valueOf(newId));
            return volunteer;
        } catch (Exception e) {
            throw new DaoException("Error creating volunteer", e);
        }
    }

    @Override
    public ShelterVolunteer update(ShelterVolunteer volunteer) {

        String sql = "UPDATE volunteers SET first_name = ?, last_name = ?, email = ?, phone_number = ? WHERE volunteer_id = ?";
        try {
            jdbcTemplate.update(
                    sql,
                    volunteer.getFirstName(),
                    volunteer.getLastName(),
                    volunteer.getEmail(),
                    volunteer.getPhoneNumber(),
                    Integer.parseInt(volunteer.getVolunteerId())
            );
            return volunteer;
        } catch (Exception e) {
            throw new DaoException("Failed to update volunteer", e);
        }
    }

    @Override
    public boolean deleteVolunteer(int volunteerId) {
        String sql = "DELETE FROM volunteers WHERE volunteer_id = ?";
        try {
            return jdbcTemplate.update(sql, volunteerId) > 0;
        } catch (Exception e) {
            throw new DaoException("Error deleting volunteer", e);
        }
    }

    private ShelterVolunteer mapRowToVolunteer(SqlRowSet rs) {

        ShelterVolunteer volunteer = new ShelterVolunteer();
        volunteer.setVolunteerId(rs.getString("volunteer_id"));
        volunteer.setFirstName(rs.getString("first_name"));
        volunteer.setLastName(rs.getString("last_name"));
        volunteer.setEmail(rs.getString("email"));
        volunteer.setPhoneNumber(rs.getString("phone_number"));

        return volunteer;
    }
}