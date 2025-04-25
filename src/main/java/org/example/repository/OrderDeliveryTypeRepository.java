package org.example.repository;

import org.example.entity.Notification;
import org.example.entity.OrderDeliveryType;
import org.example.entity.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDeliveryTypeRepository extends JpaRepository<OrderDeliveryType, Integer> {
    List<OrderDeliveryType> findOrderDeliveryTypesByStatus(Status status);
    OrderDeliveryType findOrderDeliveryTypeByIdAndStatus(int id, Status status);
}
