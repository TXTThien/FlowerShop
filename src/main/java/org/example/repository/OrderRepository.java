package org.example.repository;

import org.example.entity.Order;
import org.example.entity.OrderDetail;
import org.example.entity.enums.Condition;
import org.example.entity.enums.IsPaid;
import org.example.entity.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findOrdersByAccountIDAccountIDAndStatusOrderByOrderIDDesc(int accountid, Status status);
    Order findOrderByOrderID(int id);
    List<Order>findOrdersByShippingIsNullAndStatusAndConditionInOrderByOrderIDDesc(Status status, List<Condition> conditions);
    List<Order> findOrderByShipping_AccountID_AccountIDAndConditionInOrderByOrderIDDesc(int id, List<Condition> conditions);

    @Query("SELECT SUM(o.totalAmount) FROM Order o WHERE o.paid = :isPaid")
    BigDecimal calculateGet(@Param("isPaid") IsPaid isPaid);
}
