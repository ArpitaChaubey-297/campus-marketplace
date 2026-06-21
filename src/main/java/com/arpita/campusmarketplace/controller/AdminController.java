package com.arpita.campusmarketplace.controller;

import com.arpita.campusmarketplace.model.User;
import com.arpita.campusmarketplace.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.arpita.campusmarketplace.dto.DashboardDTO;
import com.arpita.campusmarketplace.service.AdminService;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdminService adminService;

    @GetMapping("/test")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminTest() {
        return "Welcome Admin";
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/dashboard")
    @PreAuthorize(("hasRole('ADMIN')"))
    public DashboardDTO getDashboard(){
        return adminService.getDashboard();
    }
}
