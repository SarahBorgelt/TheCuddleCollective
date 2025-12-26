package com.techelevator.services;

import com.techelevator.dao.ShelterApplicationDao;
import com.techelevator.dao.VolunteerDao;
import com.techelevator.model.ShelterApplication;
import com.techelevator.model.ShelterVolunteer;
import org.springframework.stereotype.Service;

@Service
public class ApplicationApprovalService {

    private final ShelterApplicationDao applicationDao;
    private final VolunteerDao volunteerDao;

    public ApplicationApprovalService(ShelterApplicationDao applicationDao, VolunteerDao volunteerDao) {
        this.applicationDao = applicationDao;
        this.volunteerDao = volunteerDao;
    }

    public void approve(int applicationId) {
        applicationDao.approve(applicationId);

        // Optional: auto-create ShelterVolunteer
        ShelterApplication app = applicationDao.findById(applicationId);
        ShelterVolunteer volunteer = new ShelterVolunteer();
        volunteer.setFirstName(app.getFirstName());
        volunteer.setLastName(app.getLastName());
        volunteer.setEmail(app.getEmail());
        volunteerDao.createVolunteer(volunteer);
    }

    public void deny(int applicationId) {
        applicationDao.delete(applicationId);
    }
}