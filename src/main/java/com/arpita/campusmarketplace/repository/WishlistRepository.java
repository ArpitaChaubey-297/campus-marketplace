package com.arpita.campusmarketplace.repository;

import com.arpita.campusmarketplace.model.Product;
import com.arpita.campusmarketplace.model.User;
import com.arpita.campusmarketplace.model.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.List;
import java.util.Optional;
public interface WishlistRepository extends JpaRepository<Wishlist,Long> {
    List<Wishlist> findByUser(User user);
    Optional<Wishlist> findByUserAndProduct(
            User user,
            Product product
    );

}
