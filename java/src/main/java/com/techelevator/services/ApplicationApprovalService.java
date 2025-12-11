package com.techelevator.services;

import com.techelevator.dao.ShelterApplicationDao;
import com.techelevator.dao.VolunteerDao;
import com.techelevator.model.ShelterApplication;
import com.techelevator.model.ShelterVolunteer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ApplicationApprovalService {
    private final ShelterApplicationDao shelterApplicationDao;
    private final VolunteerDao volunteerDao;
    private final EmailService emailService;
    private final PasswordService passwordService;;

    public ApplicationApprovalService(ShelterApplicationDao applicationRepo,
                                      VolunteerDao volunteerRepo, ShelterApplicationDao shelterApplicationDao, VolunteerDao volunteerDao,
                                      EmailService emailService, PasswordService passwordService) {
        this.shelterApplicationDao = shelterApplicationDao;
        this.volunteerDao = volunteerDao;
        this.passwordService = passwordService;
        this.emailService = emailService;
    }

    @Transactional
    public ShelterVolunteer approve(int applicationId) {

        ShelterApplication app = shelterApplicationDao.findById(applicationId);
        if (app == null) {
            throw new RuntimeException("Application not found");
        }

        String username = app.getFirstName().toLowerCase()
                + app.getLastName().toLowerCase()
                + app.getApplicationId();

        String tempPassword = passwordService.generateTempPassword();
        String hashed = passwordService.hashPassword(tempPassword);

        ShelterVolunteer v = new ShelterVolunteer();
        v.setFirstName(app.getFirstName());
        v.setLastName(app.getLastName());
        v.setEmail(app.getEmail());
        v.setPhoneNumber(app.getPhoneNumber());
        v.setUsername(username);
        v.setPasswordHash(hashed);
        v.setFirstLogin(true);
        v.setTemporaryPasswordActive(true);

        volunteerDao.createVolunteer(v);

        shelterApplicationDao.delete(applicationId);

        emailService.sendTempPasswordEmail(v.getEmail(), username, tempPassword);

        return v;
    }

    public void deny(int appId) {
        shelterApplicationDao.delete(appId);
    }

    }
