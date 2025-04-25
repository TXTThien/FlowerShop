package org.example.service;

import org.example.entity.OrderDelivery;

import java.util.List;

public interface IOrderDelivery {
    OrderDelivery findOrderDelivery(int orderdelivery);

    List<OrderDelivery> findOrderDeliveryByAccountID(int id);
}
