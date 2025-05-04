package org.example.service.Impl;

import lombok.RequiredArgsConstructor;
import org.example.entity.Discount;
import org.example.entity.enums.Status;
import org.example.repository.DiscountRepository;
import org.example.service.IDiscountService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DiscountServiceImpl implements IDiscountService {
    DiscountRepository discountRepository;
    @Override
    public List<Discount> findAllCode() {
        return discountRepository.findDiscountByStatus(Status.ENABLE);
    }

    @Override
    public List<Discount> findAllEnable() {
        return discountRepository.findDiscountsByStatusAndAccountIsNull(Status.ENABLE);
    }

    @Override
    public Discount findDiscountByName(String discount) {
        return discountRepository.findDiscountByDiscountcodeAndStatus(discount,Status.ENABLE);
    }

    @Override
    public Discount findDiscountByID(int discountid) {
        return discountRepository.findDiscountByDiscountIDAndStatus(discountid,Status.ENABLE);
    }
}
