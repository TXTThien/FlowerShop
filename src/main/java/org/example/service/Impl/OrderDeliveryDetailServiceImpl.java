package org.example.service.Impl;

import lombok.RequiredArgsConstructor;
import org.example.entity.OrderDeliveryDetail;
import org.example.repository.OrderDeliveryDetailRepository;
import org.example.service.INotificationService;
import org.example.service.IOrderDelivery;
import org.example.service.IOrderDeliveryDetail;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDeliveryDetailServiceImpl implements IOrderDeliveryDetail {
    private final OrderDeliveryDetailRepository orderDeliveryDetailRepository;
    @Override
    public List<OrderDeliveryDetail> findOrDeDetailByOrDeID(int id) {
        return orderDeliveryDetailRepository.findOrderDeliveryDetailsByOrderDelivery_Id(id);
    }
}
