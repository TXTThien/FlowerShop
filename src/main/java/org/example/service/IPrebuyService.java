package org.example.service;

import org.example.entity.Account;
import org.example.entity.Order;

import java.math.BigDecimal;

public interface  IPrebuyService {
    void createBillInfo(Order orderID, int cartid, BigDecimal price);

}