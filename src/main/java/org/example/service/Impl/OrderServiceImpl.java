package org.example.service.Impl;

import lombok.RequiredArgsConstructor;
import org.example.entity.Order;
import org.example.entity.OrderDelivery;
import org.example.entity.enums.Condition;
import org.example.entity.enums.IsPaid;
import org.example.entity.enums.OrDeCondition;
import org.example.entity.enums.Status;
import org.example.repository.OrderRepository;
import org.example.service.IOrderService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements IOrderService {
    private final OrderRepository orderRepository;
    @Override
    public List<Order> findOrderByAccountID(int idAccount, Status status) {
        return orderRepository.findOrdersByAccountIDAccountIDAndStatusOrderByOrderIDDesc(idAccount,Status.ENABLE);
    }

    @Override
    public Order findOrderByOrderID(int orderid) {
        return orderRepository.findOrderByOrderID(orderid);
    }

    @Override
    public List<Order> findOrderNoShipping() {
        List<Condition> conditions = new ArrayList<>();
        conditions.add(Condition.Pending);
        conditions.add(Condition.In_Transit);
        conditions.add(Condition.Prepare);
        conditions.add(Condition.Processing);
        return orderRepository.findOrdersByShippingIsNullAndStatusAndConditionIn(Status.ENABLE, conditions);
    }

    @Override
    public Order update(Order order) {
        return orderRepository.findById(order.getOrderID())
                .map(existingBanner -> {
                    existingBanner.setAccountID(order.getAccountID());
                    existingBanner.setDate(order.getDate());
                    existingBanner.setPaid(order.getPaid());
                    existingBanner.setTotalAmount(order.getTotalAmount());
                    existingBanner.setDeliveryAddress(order.getDeliveryAddress());
                    existingBanner.setStatus(order.getStatus());
                    existingBanner.setPhoneNumber(order.getPhoneNumber());
                    existingBanner.setName(order.getName());
                    existingBanner.setNote(order.getNote());
                    existingBanner.setShipping(order.getShipping());
                    existingBanner.setCondition(order.getCondition());

                    return orderRepository.save(existingBanner);
                }).orElse(null);
    }

    @Override
    public List<Order> findOrderByShipperID(int id) {
        List<Condition> conditions = new ArrayList<>();

        conditions.add(Condition.Cancelled);
        conditions.add(Condition.Cancel_is_Processing);
        conditions.add(Condition.Delivered_Successfully);
        conditions.add(Condition.Return_to_shop);

        return orderRepository.findOrderByShipping_AccountID_AccountIDAndConditionInOrderByOrderIDDesc(id,conditions);
    }

    @Override
    public List<Order> findOrderByShipperIDAndCondition(int id) {
        List<Condition> conditions = new ArrayList<>();
        conditions.add(Condition.Shipper_Delivering);
        conditions.add(Condition.First_Attempt_Failed);
        conditions.add(Condition.Second_Attempt_Failed);
        conditions.add(Condition.Third_Attempt_Failed);
        conditions.add(Condition.Pending);
        conditions.add(Condition.Processing);
        conditions.add(Condition.Prepare);
        conditions.add(Condition.In_Transit);
        return orderRepository.findOrderByShipping_AccountID_AccountIDAndConditionInOrderByOrderIDDesc(id,conditions);
    }

    @Override
    public List<Order> findOrderByCondition(Condition condition) {
        return orderRepository.findOrdersByConditionAndStatus(condition, Status.ENABLE);
    }

    @Override
    public List<Order> findOrdersByOrDeID(int orderDelivery1) {
        return orderRepository.findOrdersByOrderDelivery_IdAndConfirmAndStatus(orderDelivery1, IsPaid.Yes, Status.ENABLE);
    }

    @Override
    public List<Order> findOrdersByOrDeIDAndCondition(int id, Condition condition) {
        return orderRepository.findOrdersByOrderDelivery_IdAndCondition(id,condition);
    }

    @Override
    public List<Order> findOrderByOrDeIDAndTime(Integer id) {
        LocalDate localDate = LocalDate.now();
        LocalDateTime startOfDay = localDate.atStartOfDay(); // 00:00:00
        LocalDateTime endOfDay = localDate.atTime(LocalTime.MAX);
        return orderRepository.findOrderByOrderDelivery_IdAndDate(id,startOfDay,endOfDay, Status.ENABLE);
    }
}
