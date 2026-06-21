package com.arpita.campusmarketplace.controller;

import com.arpita.campusmarketplace.dto.PurchaseRequestDTO;
import com.arpita.campusmarketplace.model.PurchaseRequest;
import com.arpita.campusmarketplace.service.PurchaseRequestService;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/requests")
public class PurchaseRequestController {

    private final PurchaseRequestService purchaseRequestService;

    public PurchaseRequestController(
            PurchaseRequestService purchaseRequestService) {

        this.purchaseRequestService = purchaseRequestService;
    }

    @PostMapping("/{productId}")
    public String sendRequest(
            @PathVariable Long productId) {

        return purchaseRequestService.sendRequest(productId);
    }

    @GetMapping("/my")
    public List<PurchaseRequestDTO> getMyRequests(){
        return purchaseRequestService.getMyRequests();
    }

    @GetMapping("/inbox")
    public List<PurchaseRequestDTO> getInbox(){
        return purchaseRequestService.getInbox();
    }

    @PutMapping("/{requestId}/accept")
    public  String acceptRequest(
            @PathVariable Long requestId){
        return purchaseRequestService
                .acceptRequest(requestId);
    }

    @PutMapping("/{requestId}/reject")
    public String rejectRequest(
            @PathVariable Long requestId){
        return purchaseRequestService.rejectRequest(requestId);
    }
}