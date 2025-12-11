package com.techelevator.controller;

import com.techelevator.model.ShelterVolunteer;
import com.techelevator.services.ApplicationApprovalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/applications")
public class ApplicationApprovalController {

    private final ApplicationApprovalService approvalService;

    public ApplicationApprovalController(ApplicationApprovalService approvalService) {
        this.approvalService = approvalService;
    }

    @PostMapping("/{id}/approve")
    public ResponseEntity<?> approveApplication(@PathVariable int id){
        ShelterVolunteer volunteer = approvalService.approve(id);
        return ResponseEntity.ok(volunteer);
    }

    @PostMapping("/{id}/deny")
    public ResponseEntity<?> denyApplication(@PathVariable int id){
        approvalService.deny(id);
        return ResponseEntity.ok().body("Application denied.");
    }
}
