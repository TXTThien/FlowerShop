package org.example.repository;

import org.example.entity.Order;
import org.example.entity.Preorder;
import org.example.entity.Purpose;
import org.example.entity.Refund;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefundResponsitory extends JpaRepository<Refund, Integer> {
    Refund findRefundById(int id);

    boolean existsByPreorderID(Preorder preorder);

    boolean existsByOrderID(Order order);
}
