package org.example.repository;

import org.example.entity.Order;
import org.example.entity.OrderDetail;
import org.example.entity.enums.Condition;
import org.example.entity.enums.IsPaid;
import org.example.entity.enums.OrDeCondition;
import org.example.entity.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findOrdersByAccountIDAccountIDAndStatusOrderByOrderIDDesc(int accountid, Status status);
    Order findOrderByOrderID(int id);
    List<Order>findOrdersByShippingIsNullAndStatusAndConditionIn(Status status, List<Condition> conditions);
    List<Order> findOrderByShipping_AccountID_AccountIDAndConditionInOrderByOrderIDDesc(int id, List<Condition> conditions);
    List<Order> findOrdersByConditionAndStatus(Condition condition, Status status);
    @Query("SELECT SUM(o.totalAmount) FROM Order o WHERE o.paid = :isPaid")
    BigDecimal calculateGet(@Param("isPaid") IsPaid isPaid);

    List<Order> findOrdersByOrderDelivery_IdAndConfirmAndStatus(int id,  IsPaid confirm, Status status);
    List<Order> findOrdersByOrderDelivery_IdAndCondition(int id, Condition condition);
    @Query("SELECT o FROM Order o WHERE o.orderDelivery.id = :id AND o.date BETWEEN :startOfDay AND :endOfDay AND o.status =:status")
    List<Order> findOrderByOrderDelivery_IdAndDate(
            @Param("id") int id,
            @Param("startOfDay") LocalDateTime startOfDay,
            @Param("endOfDay") LocalDateTime endOfDay,
            @Param("status") Status status
    );
    List<Order> findOrdersByOrderDelivery_IdAndStatus(int id, Status status);

}
