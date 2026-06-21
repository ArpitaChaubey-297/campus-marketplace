package com.arpita.campusmarketplace.service;

import com.arpita.campusmarketplace.dto.ProductDTO;
import com.arpita.campusmarketplace.dto.ProductResponseDTO;
import com.arpita.campusmarketplace.exception.ResourceNotFoundException;
import com.arpita.campusmarketplace.model.Product;
import com.arpita.campusmarketplace.model.User;
import com.arpita.campusmarketplace.repository.ProductRepository;
import com.arpita.campusmarketplace.repository.UserRepository;
import com.arpita.campusmarketplace.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.arpita.campusmarketplace.model.ProductStatus;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    // CREATE PRODUCT
    public ProductResponseDTO createProduct(ProductDTO dto) {

        String email = SecurityUtils.getCurrentUserEmail();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with email: " + email));

        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setUser(user);
        product.setStatus(ProductStatus.AVAILABLE);
        return mapToDTO(productRepository.save(product));
    }

    // GET ALL PRODUCTS
    public List<ProductResponseDTO> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // GET BY USER
    public List<ProductResponseDTO> getProductsByUserId(Long userId) {
        return productRepository.findByUserId(userId)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // SEARCH PRODUCTS
    public List<ProductResponseDTO> searchProducts(String keyword) {
        return productRepository.findByNameContaining(keyword)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // PAGINATION
    public Page<ProductResponseDTO> getProductsPaginated(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(this::mapToDTO);
    }

    // UPDATE PRODUCT (SECURED)
    public ProductResponseDTO updateProduct(Long id, ProductDTO dto) {

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found with id: " + id));

        String email = SecurityUtils.getCurrentUserEmail();

        boolean isAdmin = SecurityContextHolder.getContext()
                .getAuthentication()
                .getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin && !product.getUser().getEmail().equals(email)) {
            throw new RuntimeException("You are not allowed to update this product");
        }

        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());

        return mapToDTO(productRepository.save(product));
    }

    // DELETE PRODUCT (SECURED)
    public void deleteProduct(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found with id: " + id));

        String email = SecurityUtils.getCurrentUserEmail();

        boolean isAdmin = SecurityContextHolder.getContext()
                .getAuthentication()
                .getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin && !product.getUser().getEmail().equals(email)) {
            throw new RuntimeException("You are not allowed to delete this product");
        }

        productRepository.delete(product);
    }

    // MAPPER
    private ProductResponseDTO mapToDTO(Product p) {

        ProductResponseDTO dto = new ProductResponseDTO();

        dto.setId(p.getId());
        dto.setName(p.getName());
        dto.setDescription(p.getDescription());
        dto.setPrice(p.getPrice());
        dto.setImageUrl(p.getImageUrl());
        if (p.getUser() != null) {
            dto.setUserId(p.getUser().getId());
            dto.setUserName(p.getUser().getName());
        }

        return dto;
    }
    public List<ProductResponseDTO> getMyProducts(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return productRepository.findByUserId(user.getId())
                .stream()
                .map(this::mapToDTO)
                .toList();
    }
    public String uploadImage(Long productId, MultipartFile file)
            throws IOException {

        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product not found"));

        String fileName =
                System.currentTimeMillis() + "_" +
                        file.getOriginalFilename();

        String uploadDir = "C:\\Users\\adiya\\OneDrive\\Pictures\\Lenovo\\LenovoNow\\icons\\";

        File directory = new File(uploadDir);

        if (!directory.exists()) {
            directory.mkdirs();
        }

        file.transferTo(new File(uploadDir + fileName));

        product.setImageUrl("icons/" + fileName);

        productRepository.save(product);

        return "Image uploaded successfully";
    }
}