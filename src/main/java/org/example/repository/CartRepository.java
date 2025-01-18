package org.example.repository;

import org.example.entity.Banner;
import org.example.entity.Cart;
import org.example.entity.enums.Status;
import org.example.entity.enums.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    List<Cart> findCartByAccountID_AccountIDAndStatusAndTypeOrderByCartIDDesc(int id, Status status, Type type);

    Cart findCartByCartID(int id);
    void deleteCartByCartID(int id);
}
