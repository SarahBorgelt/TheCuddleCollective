package com.techelevator.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class PasswordService {

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private final SecureRandom random = new SecureRandom();
    private final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";


    public String generateTempPassword() {
        StringBuilder tempPassword = new StringBuilder(10);
        for (int i = 0; i < 10; i++) {
            tempPassword.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return tempPassword.toString();
    }

    public String hashPassword(String rawPassword) {
        return encoder.encode(rawPassword);
    }

    public boolean verifyPassword(String rawPassword, String hashed) {
        return encoder.matches(rawPassword, hashed);
    }
}
