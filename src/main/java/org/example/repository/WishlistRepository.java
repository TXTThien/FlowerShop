package org.example.repository;

import org.example.entity.Wishlist;
import org.example.entity.enums.Status;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Integer> {
    List<Wishlist>findWishlistsByAccountID_AccountIDAndStatus(int id, Status status);
    Wishlist findWishlistByWishListIDAndStatus(int id, Status status);
    Wishlist findWishlistByFlowerFlowerIDAndAccountIDAccountIDAndStatus(int id,int account ,Status status);

}
