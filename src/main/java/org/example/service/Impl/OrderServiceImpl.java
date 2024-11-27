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

    @Override
    public Order findOrderByOrderID(int orderid) {
        return orderRepository.findOrderByOrderID(orderid);
    }

    @Override
    public Order update(Order order) {
        return orderRepository.findById(order.getOrderID())
                .map(existingBanner -> {
                    existingBanner.setAccountID(order.getAccountID());
                    existingBanner.setDate(order.getDate());
                    existingBanner.setPaid(order.getPaid());
                    existingBanner.setTotalAmount(order.getTotalAmount());
                    existingBanner.setDeliveryAddress(order.getDeliveryAddress());
                    existingBanner.setStatus(order.getStatus());
                    existingBanner.setPhoneNumber(order.getPhoneNumber());
                    existingBanner.setName(order.getName());
                    existingBanner.setNote(order.getNote());
                    existingBanner.setShipping(order.getShipping());
                    existingBanner.setCondition(order.getCondition());

                    return orderRepository.save(existingBanner);
                }).orElse(null);
    }
}
