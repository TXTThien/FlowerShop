package org.example.service;

import org.example.entity.Discount;

import java.util.List;

public interface IDiscountService {
    List<Discount> findAllCode();

    List<Discount> findAllEnable();

    Discount findDiscountByName(String discount);

    Discount findDiscountByID(int discountid);
}
