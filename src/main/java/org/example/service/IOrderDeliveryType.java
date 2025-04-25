package org.example.service;

import org.example.entity.OrderDeliveryType;

import java.util.List;

public interface IOrderDeliveryType {
    List<OrderDeliveryType> findAllEnable();

    OrderDeliveryType findTypeByIDEnable(int orderDeliveryTypeID);
}
