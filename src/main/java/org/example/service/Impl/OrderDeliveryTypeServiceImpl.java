package org.example.service.Impl;

import lombok.RequiredArgsConstructor;
import org.example.entity.OrderDeliveryType;
import org.example.entity.enums.Status;
import org.example.repository.OrderDeliveryTypeRepository;
import org.example.service.INotificationService;
import org.example.service.IOrderDeliveryType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDeliveryTypeServiceImpl implements IOrderDeliveryType {
    private final OrderDeliveryTypeRepository orderDeliveryTypeRepository;
    @Override
    public List<OrderDeliveryType> findAllEnable() {
        return orderDeliveryTypeRepository.findOrderDeliveryTypesByStatus(Status.ENABLE);
    }

    @Override
    public OrderDeliveryType findTypeByIDEnable(int orderDeliveryTypeID) {
        return orderDeliveryTypeRepository.findOrderDeliveryTypeByIdAndStatus(orderDeliveryTypeID,Status.ENABLE);
    }
}
