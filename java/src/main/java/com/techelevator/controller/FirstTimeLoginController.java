package com.techelevator.controller;

import com.techelevator.dao.AuthDao;
import com.techelevator.exception.DaoException;
import com.techelevator.model.FirstLoginRequest;
import com.techelevator.model.ShelterVolunteer;
import com.techelevator.services.ActivationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/first-login")
@CrossOrigin
public class FirstTimeLoginController {

    private final ActivationService activationService;
    private final AuthDao authDao;

    public FirstTimeLoginController(ActivationService activationService, AuthDao authDao) {
        this.activationService = activationService;
        this.authDao = authDao;
    }

    @PostMapping
    public void completeFirstLogin(@RequestBody FirstLoginRequest request) {

        ShelterVolunteer vol = authDao.findByUsername(request.getUsername());
        if (vol == null) {
            throw new DaoException("User not found");
        }
        if (!activationService.isFirstTimeLogin(vol)) {
            throw new DaoException("User is not eligible for first-time login");
        }

        activationService.activateAccount(vol, request.getNewPassword());
    }
}
