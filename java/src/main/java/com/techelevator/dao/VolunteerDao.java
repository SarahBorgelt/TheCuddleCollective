package com.techelevator.dao;

import com.techelevator.model.ShelterVolunteer;

import java.util.List;

public interface VolunteerDao {
    List<ShelterVolunteer> getAllVolunteers();

    public ShelterVolunteer createVolunteer(ShelterVolunteer volunteer);

    boolean deleteVolunteer(int volunteerId);

    ShelterVolunteer findByUsername(String username);

    void update(ShelterVolunteer v);
}
