package org.example.repository;

import org.example.entity.Wishlist;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Integer> {
}
