package com.techelevator.services;

import org.springframework.stereotype.Service;

@Service
public class EmailService {

    public void sendTempPasswordEmail(String toEmail, String username, String tempPassword) {
        // For development: print to console
        System.out.println("==================================");
        System.out.println("EMAIL TO: " + toEmail);
        System.out.println("Your volunteer account has been approved!");
        System.out.println("Username: " + username);
        System.out.println("Temporary Password: " + tempPassword);
        System.out.println("Please log in and reset your password.");
        System.out.println("==================================");
    }
}
    //To get recipients name, username, and generated temp password. This will send out an info email with login information
    //and instructions on how to reset password.
