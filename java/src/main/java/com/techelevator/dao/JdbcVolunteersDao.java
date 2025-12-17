package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.ShelterVolunteer;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcVolunteersDao implements VolunteerDao{

    private final JdbcTemplate jdbcTemplate;

    public JdbcVolunteersDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<ShelterVolunteer> getAllVolunteers(){
        List<ShelterVolunteer> shelterVolunteers = new ArrayList<>();
        String sql = "SELECT volunteer_id, first_name, last_name, email FROM volunteers";
        try{
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()){
            ShelterVolunteer shelterVolunteer = mapRowToVolunteer(results);
            shelterVolunteers.add(shelterVolunteer);
        }}
        catch (Exception e){
            throw new DaoException("Cannot retrieve volunteers");
        }
        return shelterVolunteers;
    }

    @Override
    public ShelterVolunteer createVolunteer(ShelterVolunteer volunteer) {
        String sql = "INSERT INTO volunteers (first_name, last_name, email) " +
                "VALUES (?, ?, ?) RETURNING volunteer_id;";
        try {
            Integer newId = jdbcTemplate.queryForObject(sql, Integer.class, volunteer.getFirstName(), volunteer.getLastName(), volunteer.getEmail()
            );
            volunteer.setVolunteerId(String.valueOf(newId));
            return volunteer;
        } catch (Exception e) {
            throw new DaoException("Error adding volunteer", e);
        }
    }

    @Override
    public boolean deleteVolunteer(int volunteerId) {
        String sql = "DELETE FROM volunteers WHERE volunteer_id = ?;";

        try {
            int rowsAffected = jdbcTemplate.update(sql, volunteerId);
            return rowsAffected > 0;
        } catch (Exception e) {
            throw new DaoException("Error deleting volunteer", e);
        }
    }

    @Override
    public ShelterVolunteer update(ShelterVolunteer volunteer) {
        try {
            String sql = "UPDATE volunteers " +
                    "SET first_name = ?, last_name = ?, email = ? " +
                    "WHERE volunteer_id = ?";

            jdbcTemplate.update(sql,
                    volunteer.getFirstName(),
                    volunteer.getLastName(),
                    volunteer.getEmail(),
                    Integer.parseInt(volunteer.getVolunteerId())
            );
            return volunteer;
        } catch (DaoException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to update volunteer ", e);
        }
    }

    @Override
    public ShelterVolunteer findByUsername(String username) {
        String sql = "SELECT volunteer_id, first_name, last_name, email, username, password_hash, temp_password_active, first_login " +
                "FROM volunteers WHERE username = ?";

        SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, username);

        if (rs.next()) {
            return mapRowToVolunteer(rs);
        }
        return null;  // username not found
    }

    private ShelterVolunteer mapRowToVolunteer(SqlRowSet rs){
        ShelterVolunteer shelterVolunteer = new ShelterVolunteer();
        shelterVolunteer.setVolunteerId(rs.getString("volunteer_id"));
        shelterVolunteer.setFirstName(rs.getString("first_name"));
        shelterVolunteer.setLastName(rs.getString("last_name"));
        shelterVolunteer.setEmail(rs.getString("email"));

        return shelterVolunteer;
    }
}
