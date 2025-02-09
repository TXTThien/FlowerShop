package org.example.service.Impl;

import lombok.RequiredArgsConstructor;
import org.example.entity.Refund;
import org.example.repository.RefundResponsitory;
import org.example.service.IRefundService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefundServiceImpl implements IRefundService {
    private final RefundResponsitory refundResponsitory;
    @Override
    public Refund findRefundbyID(int id) {
        return refundResponsitory.findRefundById(id);
    }
}
