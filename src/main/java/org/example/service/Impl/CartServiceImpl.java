package org.example.service.Impl;

import lombok.RequiredArgsConstructor;
import org.example.entity.Cart;
import org.example.entity.enums.Status;
import org.example.repository.CartRepository;
import org.example.service.ICartService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements ICartService {
    private final CartRepository cartRepository;

    @Override
    public Cart createCart(Cart cart) {
        if (cart.getFlowerSize() == null) {
            throw new IllegalArgumentException("ProductSizeID is required");
        }
        return cartRepository.save(cart);
    }

    @Override
    public Cart getCartById(Integer id) {
        return cartRepository.findById(id).orElse(null);
    }

    @Override
    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }

    @Override
    public Cart updateCart(Integer id, Cart cart) {
        Optional<Cart> existingCart = cartRepository.findById(id);
        if (existingCart.isPresent()) {
            if (cart.getFlowerSize() == null) {
                throw new IllegalArgumentException("ProductSizeID is required");
            }
            cart.setCartID(id);
            return cartRepository.save(cart);
        }
        return null;
    }

    @Override
    public void deleteCart(Integer cartId) {
        Optional<Cart> cartOptional = cartRepository.findById(cartId);
        if (cartOptional.isPresent()) {
            Cart cart = cartOptional.get();
            cart.setStatus(Status.DISABLE); // Cập nhật trạng thái thành Disable
            cartRepository.save(cart); // Lưu lại thay đổi vào cơ sở dữ liệu
        }
    }

    @Override
    @Transactional
    public void deleteBoughtCart(Integer id) {
        cartRepository.deleteCartByCartID(id);
    }

    @Override
    @Transactional
    public void hardDeleteCart(Integer id) {
        cartRepository.deleteCartByCartID(id);
    }

    @Override
    public List<Cart> findCartsByAccountID(int id) {
        return cartRepository.findCartByAccountID_AccountIDAndStatusOrderByCartIDDesc(id,Status.ENABLE);
    }

    @Override
    public Cart findCartByCartID(int cartID) {
        return cartRepository.findCartByCartID(cartID);
    }
}
