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
    public AvailablePet getAvailablePetByBreed(String breed) {
        return null;
    }

    @Override
    public AvailablePet getAvailablePetByColor(String color) {
        return null;
    }

    @Override
    public AvailablePet getAvailablePetByAge(Integer age) {
        return null;
    }

    @Override
    public AvailablePet getAvailablePetByType(String type) {
        return null;
    }

    @Override
    public AvailablePet getAvailablePetByAdoptionStatus(String adoptionStatus) {
        return null;
    }

    @Override
    public void addPet(AvailablePet pet) {

    }

    @Override
    public void updatePet(AvailablePet pet) {

    }

    //TODO How do we add pets from available to adopted table? Use insert statement to add
    //pet to adopted table and delete statement to remove from current table

    @Override
    public List<AvailablePet> getAllPets() {
        List<AvailablePet> pets = new ArrayList<>();
        String sql = "SELECT animal_id, animal_type, breed, color, age, name, adoption_status, image_url FROM available_pets";
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
        //The below still need built out
        //        this.animalName = animalName;
        //        this.adoptionStatus = adoptionStatus;
        //        this.imageUrl = imageUrl;
        //        this.imageUrl1 = imageUrl1;
        //        this.imageUrl2 = imageUrl2;
        return availablePet;
    }
}
