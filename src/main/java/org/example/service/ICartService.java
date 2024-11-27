package org.example.service;

import org.example.entity.Cart;

import java.util.List;

public interface ICartService {
    List<Cart> getAllCarts();
    Cart getCartById(Integer id);
    Cart createCart(Cart cart);
    Cart updateCart(Integer id, Cart cartDetails);
    void deleteCart(Integer id);
    void deleteBoughtCart(Integer id);

    void hardDeleteCart(Integer id);

    List<Cart> findCartsByAccountID(int id);

    Cart findCartByCartID(int cartID);
}
