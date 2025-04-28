package org.example.service;

import org.example.entity.OrderDelivery;
import org.example.entity.enums.OrDeCondition;

import java.util.List;

public interface IOrderDelivery {
    OrderDelivery findOrderDelivery(int orderdelivery);
    List<OrderDelivery> findAllOrDeStaffAdmin();
    List<OrderDelivery> findOrderDeliveryByAccountID(int id);
    List<OrderDelivery> findOrDeByCondition(OrDeCondition orDeCondition);

    OrderDelivery findOrderDeliveryByAdmin(int id);
}
