package org.example.repository;

import org.example.entity.Order;
import org.example.entity.OrderDetail;
import org.example.entity.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findOrdersByAccountIDAccountIDAndStatusOrderByOrderIDDesc(int accountid, Status status);
}
