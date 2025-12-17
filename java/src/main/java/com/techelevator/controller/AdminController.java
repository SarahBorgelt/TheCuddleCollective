package com.techelevator.controller;

import com.techelevator.dao.ShelterApplicationDao;
import com.techelevator.model.ShelterApplication;
import com.techelevator.model.ShelterVolunteer;
import com.techelevator.services.ApplicationApprovalService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/admin/applications")
//@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final ShelterApplicationDao applicationDao;
    private final ApplicationApprovalService approvalService;

    public AdminController(ShelterApplicationDao applicationDao,
                           ApplicationApprovalService approvalService) {
        this.applicationDao = applicationDao;
        this.approvalService = approvalService;
    }

    @GetMapping("/pending")
    public List<ShelterApplication> getPendingApplications() {
        return applicationDao.findAllPending();
    }

    @PostMapping("/{id}/approve")
    public ShelterVolunteer approveApplication(@PathVariable int id) {
        return approvalService.approve(id);
    }

    @PostMapping("/{id}/deny")
    public void denyApplication(@PathVariable int id) {
        approvalService.deny(id);
    }

    @GetMapping("/approved")
    public List<ShelterApplication> approvedWithCodes(){
        return applicationDao.findAllApprovedWithInviteCode();
    }
}