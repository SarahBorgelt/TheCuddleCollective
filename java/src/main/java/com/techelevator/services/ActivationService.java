package com.techelevator.services;

import com.techelevator.dao.VolunteerDao;
import com.techelevator.model.ShelterVolunteer;
import org.springframework.stereotype.Service;

@Service
public class ActivationService {

    private final VolunteerDao volunteerDao;
    private final PasswordService passwordService;

    public ActivationService(VolunteerDao volunteerDao, PasswordService passwordService) {
        this.volunteerDao = volunteerDao;
        this.passwordService = passwordService;
    }

    public boolean isFirstTimeLogin(ShelterVolunteer volunteer){
        return volunteer.isFirstLogin() && volunteer.isTemporaryPasswordActive();
    }

    public boolean verifyTempPassword(ShelterVolunteer volunteer, String rawPassword){
        return passwordService.verifyPassword(rawPassword, volunteer.getPasswordHash());
    }

    public void activateAccount(ShelterVolunteer volunteer, String newPassword){
        String hashed = passwordService.hashPassword(newPassword);

        volunteer.setPasswordHash(hashed);
        volunteer.setTemporaryPasswordActive(false);
        volunteer.setFirstLogin(false);

        volunteerDao.update(volunteer);
    }
}
