package com.arpita.campusmarketplace.repository;
import com.arpita.campusmarketplace.model.Product;
import com.arpita.campusmarketplace.model.User;
import com.arpita.campusmarketplace.model.PurchaseRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PurchaseRequestRepository extends JpaRepository<PurchaseRequest,Long> {
    Optional<PurchaseRequest> findByBuyerAndProduct(User buyer, Product product);
    List<PurchaseRequest> findByBuyer(User user);
    List<PurchaseRequest> findBySeller(User seller);
    List<PurchaseRequest> findByProduct(Product product);
}
