package org.example.repository;

import org.example.entity.Banner;
import org.example.entity.Cart;
import org.example.entity.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    List<Cart> findCartByAccountID_AccountIDAndStatusOrderByCartIDDesc(int id, Status status);

    Cart findCartByCartID(int id);
    void deleteCartByCartID(int id);
}
