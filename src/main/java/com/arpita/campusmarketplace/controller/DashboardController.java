package com.arpita.campusmarketplace.controller;

import com.arpita.campusmarketplace.dto.DashboardResponseDTO;
import com.arpita.campusmarketplace.model.User;
import com.arpita.campusmarketplace.repository.ProductRepository;
import com.arpita.campusmarketplace.repository.UserRepository;
import com.arpita.campusmarketplace.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public DashboardResponseDTO getDashboard() {

        String email = SecurityUtils.getCurrentUserEmail();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        DashboardResponseDTO dto = new DashboardResponseDTO();

        dto.setUserName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setTotalProducts(
                productRepository.findByUserId(user.getId()).size()
        );

        return dto;
    }
}