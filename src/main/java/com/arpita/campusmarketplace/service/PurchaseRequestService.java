package com.arpita.campusmarketplace.service;

import com.arpita.campusmarketplace.dto.PurchaseRequestDTO;
import com.arpita.campusmarketplace.exception.ResourceNotFoundException;
import com.arpita.campusmarketplace.model.*;
import com.arpita.campusmarketplace.repository.ProductRepository;
import com.arpita.campusmarketplace.repository.PurchaseRequestRepository;
import com.arpita.campusmarketplace.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseRequestService {

    private final PurchaseRequestRepository purchaseRequestRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public PurchaseRequestService(
            PurchaseRequestRepository purchaseRequestRepository,
            ProductRepository productRepository,
            UserRepository userRepository) {

        this.purchaseRequestRepository = purchaseRequestRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public String sendRequest(Long productId) {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User buyer = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product not found"));

        User seller = product.getUser();

        if (buyer.getId().equals(seller.getId())) {
            throw new RuntimeException(
                    "You cannot send request for your own product");
        }

        if (purchaseRequestRepository
                .findByBuyerAndProduct(buyer, product)
                .isPresent()) {

            return "Request already sent";
        }

        PurchaseRequest request =
                new PurchaseRequest();

        request.setBuyer(buyer);
        request.setSeller(seller);
        request.setProduct(product);
        request.setStatus(RequestStatus.PENDING);

        purchaseRequestRepository.save(request);

        return "Purchase request sent successfully";
    }
    public List<PurchaseRequestDTO> getMyRequests(){
        String email=SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
        User buyer=userRepository.findByEmail(email)
                .orElseThrow(()-> new RuntimeException("User not found"));
        return purchaseRequestRepository.findByBuyer(buyer)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }
    public List<PurchaseRequestDTO> getInbox(){
        String email=SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
        User seller=userRepository.findByEmail(email)
                .orElseThrow(()-> new RuntimeException("User not found"));
        return purchaseRequestRepository.findBySeller(seller)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }
    public String acceptRequest(Long requestId) {

        PurchaseRequest request =
                purchaseRequestRepository.findById(requestId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Request not found"));

        Product product = request.getProduct();

        product.setStatus(ProductStatus.SOLD);

        productRepository.save(product);

        request.setStatus(RequestStatus.ACCEPTED);

        purchaseRequestRepository.save(request);

        List<PurchaseRequest> allRequests =
                purchaseRequestRepository
                        .findByProduct(product);

        for (PurchaseRequest r : allRequests) {

            if (!r.getId().equals(requestId)) {

                r.setStatus(RequestStatus.REJECTED);

                purchaseRequestRepository.save(r);
            }
        }

        return "Request accepted successfully";
    }
    public String rejectRequest(Long requestId){
        PurchaseRequest request=purchaseRequestRepository.findById(requestId)
                .orElseThrow(()->new RuntimeException("Request not found"));
        if(request.getStatus()!=RequestStatus.PENDING){
            throw new RuntimeException("Only pending requests can be rejected");
        }
        request.setStatus(RequestStatus.REJECTED);
        purchaseRequestRepository.save(request);
        return "Request rejected successfully";
    }
    private PurchaseRequestDTO mapToDTO(
            PurchaseRequest request) {

        PurchaseRequestDTO dto =
                new PurchaseRequestDTO();

        dto.setRequestId(
                request.getId());

        dto.setBuyerName(
                request.getBuyer().getName());

        dto.setSellerName(
                request.getSeller().getName());

        dto.setProductName(
                request.getProduct().getName());

        dto.setStatus(
                request.getStatus().name());

        return dto;
    }
}