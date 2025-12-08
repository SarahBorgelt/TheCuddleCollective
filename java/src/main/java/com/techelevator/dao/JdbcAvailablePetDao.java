package com.techelevator.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.techelevator.exception.DaoException;
import com.techelevator.model.AvailablePet;
import com.techelevator.model.User;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

public class JdbcAvailablePetDao implements AvailablePetDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcAvailablePetDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<AvailablePet> getAvailablePetsByBreed(String breed) {
        List<AvailablePet> petsByBreed = new ArrayList<>();
        String sql = "SELECT animal_id, animal_type, breed, color, age, name, " +
                "adoption_status, image_url FROM available_pets" +
                "where breed = ? AND adoption_status = 'available'";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, breed);
        while(results.next()) {
            AvailablePet pet = mapRowToAvailablePet(results);
            petsByBreed.add(pet);
        }
        return petsByBreed;
    }


    @Override
    public List<AvailablePet> getAvailablePetsByColor(String color) {
        List<AvailablePet> petsByColor = new ArrayList<>();
        String sql = "SELECT animal_id, animal_type, breed, color, age, name, " +
                "adoption_status, image_url FROM available_pets" +
                "where color = ? AND adoption_status = 'available'";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, color);
        while(results.next()) {
            AvailablePet pet = mapRowToAvailablePet(results);
            petsByColor.add(pet);
        }
        return petsByColor;
    }

    @Override
    public List<AvailablePet> getAvailablePetsByAge(Integer age) {
        List<AvailablePet> petsByAge = new ArrayList<>();
        String sql = "SELECT animal_id, animal_type, breed, color, age, name, " +
                "adoption_status, image_url FROM available_pets" +
                "where age = ? AND adoption_status = 'available'";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, age);
        while(results.next()) {
            AvailablePet pet = mapRowToAvailablePet(results);
            petsByAge.add(pet);
        }
        return petsByAge;
    }

    @Override
    public List<AvailablePet> getAvailablePetsByType(String type) {
        List<AvailablePet> petsByType = new ArrayList<>();
        String sql = "SELECT animal_id, animal_type, breed, color, age, name, " +
                "adoption_status, image_url FROM available_pets" +
                "where type = ? AND adoption_status = 'available'";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, type);
        while(results.next()) {
            AvailablePet pet = mapRowToAvailablePet(results);
            petsByType.add(pet);
        }
        return petsByType;
    }


    @Override
    public List<AvailablePet> getAvailablePetsByAdoptionStatus(String adoptionStatus) {
        List<AvailablePet> petsByStatus = new ArrayList<>();
        String sql = "SELECT animal_id, animal_type, breed, color, age, name, " +
                "adoption_status, image_url FROM available_pets" +
                "where adoption_status = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, adoptionStatus);
        while(results.next()) {
            AvailablePet pet = mapRowToAvailablePet(results);
            petsByStatus.add(pet);
        }
        return petsByStatus;
    }


    @Override
    public AvailablePet addPet(AvailablePet pet) {
        AvailablePet returnedPet = new AvailablePet();
        String sql = "INSERT INTO available_pets (animal_type, breed, color, age, name, " +
                "adoption_status, image_url, image_url1, image_url2"+
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        //finishing
return returnedPet;
    }

    //finishing
    @Override
    public void updatePet(AvailablePet pet) {

    }

    //TODO How do we add pets from available to adopted table? Use insert statement to add
    //pet to adopted table and delete statement to remove from current table

    @Override
    public List<AvailablePet> getAllPets() {
        List<AvailablePet> pets = new ArrayList<>();
        String sql = "SELECT animal_id, animal_type, breed, color, age, " +
                "name, adoption_status, image_url FROM available_pets";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while(results.next()) {
            AvailablePet pet = mapRowToAvailablePet(results);
            pets.add(pet);
        }
        return pets;
    }

    //TODO
    private AvailablePet mapRowToAvailablePet(SqlRowSet rs) {
        AvailablePet availablePet = new AvailablePet();
        availablePet.setAnimalId(rs.getLong("animal_id"));
        availablePet.setAnimalType(rs.getString("animal_type"));
        availablePet.setAnimalBreed(rs.getString("breed"));
        availablePet.setAnimalColor(rs.getString("color"));
        availablePet.setAnimalAge(rs.getInt("age"));
        availablePet.setAnimalName(rs.getString("name"));
        availablePet.setAdoptionStatus(rs.getString("adoption_status"));
        availablePet.setImageUrl(rs.getString("image_url"));
        availablePet.setImageUrl1(rs.getString("image_url1"));
        availablePet.setImageUrl2(rs.getString("image_url2"));
        return availablePet;
    }
}
