package org.example.service.Impl;

import lombok.RequiredArgsConstructor;
import org.example.entity.OrderDetail;
import org.example.entity.enums.Status;
import org.example.repository.OrderDetailRepository;
import org.example.service.IOrderDetailService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderDetailServiceImpl implements IOrderDetailService {
    private final OrderDetailRepository orderDetailRepository;

    @Override
    public List<OrderDetail> getAllBillInfos() {
        return orderDetailRepository.findAll();
    }

    @Override
    public Optional<OrderDetail> getBillInfoById(Integer billInfoId) {
        return orderDetailRepository.findById(billInfoId);
    }

    @Override
    public OrderDetail updateBillInfo(OrderDetail billInfo) {
        return orderDetailRepository.save(billInfo);
    }

    @Override
    public void deleteBillInfo(Integer billInfoId) {
        Optional<OrderDetail> billInfoOptional = orderDetailRepository.findById(billInfoId);
        if (billInfoOptional.isPresent()) {
            OrderDetail billInfo = billInfoOptional.get();
            billInfo.setStatus(Status.DISABLE); // Chuyển trạng thái thành Disable
            orderDetailRepository.save(billInfo); // Lưu lại thay đổi vào cơ sở dữ liệu
        }
    }

    public List<OrderDetail> findBillInfoByAccountID(int id, Status status) {
        return orderDetailRepository.findDistinctOrderDetailsByAccountIDAndStatus(id, status);
    }
}
