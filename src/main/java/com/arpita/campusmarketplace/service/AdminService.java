package com.arpita.campusmarketplace.service;

import com.arpita.campusmarketplace.dto.DashboardDTO;
import com.arpita.campusmarketplace.model.ProductStatus;
import com.arpita.campusmarketplace.repository.ProductRepository;
import com.arpita.campusmarketplace.repository.PurchaseRequestRepository;
import com.arpita.campusmarketplace.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final PurchaseRequestRepository purchaseRequestRepository;

    public AdminService(
            UserRepository userRepository,
            ProductRepository productRepository,
            PurchaseRequestRepository purchaseRequestRepository) {

        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.purchaseRequestRepository = purchaseRequestRepository;
    }

    public DashboardDTO getDashboard() {

        DashboardDTO dto = new DashboardDTO();

        dto.setTotalUsers(
                userRepository.count());

        dto.setTotalProducts(
                productRepository.count());

        dto.setTotalRequests(
                purchaseRequestRepository.count());

        dto.setSoldProducts(
                productRepository
                        .countByStatus(ProductStatus.SOLD));

        dto.setAvailableProducts(
                productRepository.countByStatus(ProductStatus.AVAILABLE)
        );

        return dto;
    }
}
