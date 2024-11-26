package org.example.service;

import org.example.entity.Order;
import org.example.entity.enums.Status;

import java.util.List;

public interface IOrderService {
    List<Order> findOrderByAccountID(int idAccount, Status status);
    Order findOrderByOrderID (int orderid);

    Order update(Order order);
}
