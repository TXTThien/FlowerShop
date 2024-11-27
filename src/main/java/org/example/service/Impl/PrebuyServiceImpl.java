package org.example.service.Impl;

import lombok.RequiredArgsConstructor;
import org.example.entity.*;
import org.example.entity.enums.Status;
import org.example.repository.AccountRepository;
import org.example.repository.OrderDetailRepository;
import org.example.repository.OrderRepository;
import org.example.repository.CartRepository;
import org.example.service.ICartService;
import org.example.service.IPrebuyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PrebuyServiceImpl implements IPrebuyService {
    private final OrderRepository billRepository;
    private final OrderDetailRepository billInfoRepository;
    private final ICartService cartService;
    private final AccountRepository accountRepository;


    public void createBillInfo(Order billID, int cartid, BigDecimal price) {
        Cart cart = cartService.findCartByCartID(cartid);
        FlowerSize FlowerSizeid = cart.getFlowerSize();
        int count = cart.getQuantity();
        cartService.hardDeleteCart(cartid);
        OrderDetail billInfo = new OrderDetail();
        billInfo.setOrderID(billID);
        billInfo.setFlowerSize(FlowerSizeid);
        billInfo.setQuantity(count);
        billInfo.setStatus(Status.ENABLE);
        billInfo.setPrice(price);
        billInfoRepository.save(billInfo);
    }

}