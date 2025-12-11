package com.techelevator.controller;

import com.techelevator.model.AvailablePet;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.ArrayList;
import java.util.List;

public class ToBeAdded
{
    // Celebrate Adopted Pets
        //Interface Code
    // List<AvailablePet> getAllAdoptedPets();
        //Jdbc Code

//    List<AvailablePet> getAllAdoptedPets(){
//        List<AvailablePet> adoptedPets = new ArrayList<>();
//        String sql = "SELECT animal_id, animal_type, breed, color, age, " +
//                "name, image_url, image_url1, image_url2 FROM available_pets where adoption_status = 'approved'";
//        SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
//        while(result.next()){
//            AvailablePet pet = mapRowToAvailablePet(result);
//            adoptedPets.add(pet);
//        }
//        return adoptedPets;
//    }

    // Multiple Photos Per Pet


}
