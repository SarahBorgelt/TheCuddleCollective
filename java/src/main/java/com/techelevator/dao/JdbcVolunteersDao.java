package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.ShelterVolunteer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

public class JdbcVolunteersDao implements VolunteerDao{

    private final JdbcTemplate jdbcTemplate;

    public JdbcVolunteersDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @PreAuthorize("hasRole('user')") //TODO review this, I'm not entirely sure it's correct?
    public List<ShelterVolunteer> getAllVolunteers(){
        List<ShelterVolunteer> shelterVolunteers = new ArrayList<>();
        String sql = "SELECT first_name, last_name, email FROM volunteers";
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

    private ShelterVolunteer mapRowToVolunteer(SqlRowSet rs){
        ShelterVolunteer shelterVolunteer = new ShelterVolunteer();
        shelterVolunteer.setVolunteerId(rs.getString("volunteer_id"));
        shelterVolunteer.getFirstName(rs.getString("first_name"));
        shelterVolunteer.getLastName(rs.getString("last_name"));
        shelterVolunteer.getEmail(rs.getString("email"));

        return shelterVolunteer;
    }
}
