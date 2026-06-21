package com.arpita.campusmarketplace.service;

import com.arpita.campusmarketplace.repository.ProductRepository;
import com.arpita.campusmarketplace.repository.UserRepository;
import com.arpita.campusmarketplace.repository.WishlistRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.arpita.campusmarketplace.model.Product;
import com.arpita.campusmarketplace.model.User;
import com.arpita.campusmarketplace.model.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import com.arpita.campusmarketplace.exception.ResourceNotFoundException;
import java.util.List;
@Service
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public WishlistService(
            WishlistRepository wishlistRepository,
            ProductRepository productRepository,
            UserRepository userRepository) {

        this.wishlistRepository = wishlistRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }
    public String addToWishlist(Long productId) {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product not found"));

        if (wishlistRepository
                .findByUserAndProduct(user, product)
                .isPresent()) {

            return "Product already in wishlist";
        }

        Wishlist wishlist = new Wishlist();

        wishlist.setUser(user);
        wishlist.setProduct(product);

        wishlistRepository.save(wishlist);

        return "Product added to wishlist";
    }
    public List<Wishlist> getWishlist(){
        String email=SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
        User user =userRepository.findByEmail(email)
                .orElseThrow(()->new RuntimeException("User not found"));
        return wishlistRepository.findByUser(user);
    }
    public String removeFromWishlist(Long productId) {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product not found"));

        Wishlist wishlist = wishlistRepository
                .findByUserAndProduct(user, product)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Product not found in wishlist"));

        wishlistRepository.delete(wishlist);

        return "Product removed from wishlist";
    }
}

