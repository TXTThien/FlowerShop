package org.example.service;

import org.example.entity.Order;
import org.example.entity.enums.Condition;
import org.example.entity.enums.Status;

import java.time.LocalDate;
import java.util.List;

public interface IOrderService {
    List<Order> findOrderByAccountID(int idAccount, Status status);
    Order findOrderByOrderID (int orderid);

    List<Order> findOrderNoShipping();
    Order update(Order order);

    List<Order> findOrderByShipperID(int id);
    List<Order> findOrderByShipperIDAndCondition(int id);

    List<Order> findOrderByCondition(Condition condition);

    List<Order> findOrdersByOrDeID(int orderDelivery1);

    List<Order> findOrdersByOrDeIDAndCondition(int id, Condition condition);

    List<Order> findOrdersByOrDeIDEnable(Integer id);
}
