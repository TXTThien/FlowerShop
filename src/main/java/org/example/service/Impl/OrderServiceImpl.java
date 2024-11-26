package org.example.service.Impl;

import lombok.RequiredArgsConstructor;
import org.example.entity.Order;
import org.example.entity.enums.Status;
import org.example.repository.OrderRepository;
import org.example.service.IOrderService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements IOrderService {
    private final OrderRepository orderRepository;
    @Override
    public List<Order> findOrderByAccountID(int idAccount, Status status) {
        return orderRepository.findOrdersByAccountIDAccountIDAndStatusOrderByOrderIDDesc(idAccount,Status.ENABLE);
    }
}
