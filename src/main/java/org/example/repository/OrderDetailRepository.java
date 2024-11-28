package org.example.repository;

import org.example.entity.OrderDetail;
import org.example.entity.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {
    @Query("SELECT DISTINCT od " +
            "FROM OrderDetail od " +
            "JOIN Order o ON od.orderID = o " +
            "WHERE o.accountID.accountID = :accountid " +
            "AND od.status = :status " +
            "ORDER BY od.orderdetailID DESC")
    List<OrderDetail> findDistinctOrderDetailsByAccountIDAndStatus(
            @Param("accountid") int accountid,
            @Param("status") Status status);

    List<OrderDetail> findOrderDetailsByOrderID_OrderIDAndStatus(int id, Status status);
}
