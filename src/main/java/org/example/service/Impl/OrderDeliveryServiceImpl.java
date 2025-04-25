package org.example.service.Impl;

import lombok.RequiredArgsConstructor;
import org.example.entity.OrderDelivery;
import org.example.entity.enums.Status;
import org.example.repository.OrderDeliveryRepository;
import org.example.service.INotificationService;
import org.example.service.IOrderDelivery;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderDeliveryServiceImpl implements IOrderDelivery {
    private final OrderDeliveryRepository orderDeliveryRepository;
    @Override
    public OrderDelivery findOrderDelivery(int orderdelivery) {
        return orderDeliveryRepository.findOrderDeliveryByIdAndStatus(orderdelivery, Status.ENABLE);
    }
}
