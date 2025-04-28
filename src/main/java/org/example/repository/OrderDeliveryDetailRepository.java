package org.example.repository;

import org.example.entity.Notification;
import org.example.entity.OrderDeliveryDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDeliveryDetailRepository extends JpaRepository<OrderDeliveryDetail, Integer> {
    List<OrderDeliveryDetail> findOrderDeliveryDetailsByOrderDelivery_Id(int id);
}
