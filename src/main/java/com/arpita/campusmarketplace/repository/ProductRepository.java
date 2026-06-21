package com.arpita.campusmarketplace.repository;

import com.arpita.campusmarketplace.model.Product;
import com.arpita.campusmarketplace.model.ProductStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.arpita.campusmarketplace.model.ProductStatus;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByUserId(Long userId);

    List<Product> findByNameContaining(String keyword);

    List<Product> findByNameContainingIgnoreCase(String keyword);

    Page<Product> findAll(Pageable pageable);

    long countByStatus(ProductStatus status);
}