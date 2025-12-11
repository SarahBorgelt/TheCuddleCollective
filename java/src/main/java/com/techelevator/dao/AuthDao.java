package com.techelevator.dao;

import com.techelevator.model.ShelterVolunteer;

public interface AuthDao {
    ShelterVolunteer findByUsername(String username);

}
