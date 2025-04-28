package org.example.service;

import org.example.entity.OrderDeliveryDetail;

import java.util.List;

public interface IOrderDeliveryDetail {
    List<OrderDeliveryDetail> findOrDeDetailByOrDeID(int id);
}
