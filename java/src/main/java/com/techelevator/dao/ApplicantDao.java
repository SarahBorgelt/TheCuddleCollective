package com.techelevator.dao;

import com.techelevator.model.Applicant;

public interface ApplicantDao {

    Applicant submitApplication(Applicant applicant);

    boolean applyInviteCode(String username, String inviteCode);
}
