package org.example.service;

import org.example.entity.Order;
import org.example.entity.OrderDetail;
import org.example.entity.enums.Status;

import java.util.List;
import java.util.Optional;

public interface IOrderDetailService {
    List<OrderDetail> getAllBillInfos();
    Optional<OrderDetail> getBillInfoById(Integer billInfoId);
    void deleteBillInfo(Integer billInfoId);

    OrderDetail updateBillInfo(OrderDetail billInfo);

    List<OrderDetail>findBillInfoByAccountID (int id, Status status);

    List<OrderDetail> findOrderDetailByOrderID(int id);
}
