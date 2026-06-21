package com.arpita.campusmarketplace.controller;

import com.arpita.campusmarketplace.dto.ProductDTO;
import com.arpita.campusmarketplace.dto.ProductResponseDTO;
import com.arpita.campusmarketplace.response.ApiResponse;
import com.arpita.campusmarketplace.security.SecurityUtils;
import com.arpita.campusmarketplace.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // CREATE
    @PostMapping
    public ApiResponse<ProductResponseDTO> create(@RequestBody @Valid ProductDTO dto) {
        return new ApiResponse<>("Product created", 201,
                productService.createProduct(dto));
    }

    // GET ALL
    @GetMapping
    public ApiResponse<List<ProductResponseDTO>> getAll() {
        return new ApiResponse<>("Products fetched", 200,
                productService.getAllProducts());
    }

    // GET BY USER
    @GetMapping("/user/{userId}")
    public ApiResponse<List<ProductResponseDTO>> getByUser(@PathVariable Long userId) {
        return new ApiResponse<>("User products fetched", 200,
                productService.getProductsByUserId(userId));
    }

    // SEARCH
    @GetMapping("/search")
    public ApiResponse<List<ProductResponseDTO>> search(@RequestParam String keyword) {
        return new ApiResponse<>("Search results", 200,
                productService.searchProducts(keyword));
    }

    // PAGINATION
    @GetMapping("/page")
    public ApiResponse<Page<ProductResponseDTO>> getPaginated(Pageable pageable) {
        return new ApiResponse<>("Paginated products", 200,
                productService.getProductsPaginated(pageable));
    }

    @GetMapping("/my-products")
    public List<ProductResponseDTO> getMyProducts(){
        String email= SecurityUtils.getCurrentUserEmail();
        return productService.getMyProducts(email);
    }
    // UPDATE
    @PutMapping("/{id}")
    public ApiResponse<ProductResponseDTO> update(@PathVariable Long id,
                                                  @RequestBody ProductDTO dto) {
        return new ApiResponse<>("Product updated", 200,
                productService.updateProduct(id, dto));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(@PathVariable Long id) {
        productService.deleteProduct(id);
        return new ApiResponse<>("Product deleted", 200, "Success");
    }

    @PostMapping("/{id}/image")
    public String uploadImage(
            @PathVariable Long id,
            @RequestParam("file")
            MultipartFile file) throws IOException{
        return productService.uploadImage(id,file);
    }
}