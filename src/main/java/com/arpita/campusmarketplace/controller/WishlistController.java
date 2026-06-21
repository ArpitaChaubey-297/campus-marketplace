package com.arpita.campusmarketplace.controller;

import com.arpita.campusmarketplace.model.Wishlist;
import com.arpita.campusmarketplace.service.WishlistService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wishlist")
public class WishlistController {

    private final WishlistService wishlistService;

    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @PostMapping("/{productId}")
    public String addToWishlist(
            @PathVariable Long productId) {

        return wishlistService.addToWishlist(productId);
    }

    @GetMapping
    public List<Wishlist> getWishlist(){
        return wishlistService.getWishlist();
    }

    @DeleteMapping("/{productId}")
    public String removeFromWishlist(
            @PathVariable Long productId){
        return wishlistService.removeFromWishlist(productId);
    }
}