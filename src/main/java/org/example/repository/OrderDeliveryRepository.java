package org.example.repository;

import org.example.entity.Notification;
import org.example.entity.OrderDelivery;
import org.example.entity.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDeliveryRepository extends JpaRepository<OrderDelivery, Integer> {
    OrderDelivery findOrderDeliveryByIdAndStatus(int id, Status status);
    List<OrderDelivery> findOrderDeliveriesByAccountID_AccountIDAndStatus(int id, Status status);
}
