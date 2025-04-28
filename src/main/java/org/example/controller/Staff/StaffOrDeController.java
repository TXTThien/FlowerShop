package org.example.controller.Staff;

import lombok.RequiredArgsConstructor;
import org.example.entity.*;
import org.example.entity.enums.*;
import org.example.repository.OrderDetailRepository;
import org.example.repository.OrderRepository;
import org.example.service.IOrderDelivery;
import org.example.service.IOrderDeliveryDetail;
import org.example.service.IOrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/staff/orde")
@RequiredArgsConstructor
public class StaffOrDeController {
    private final IOrderDelivery orderDeliveryService;
    private final IOrderDeliveryDetail orderDeliveryDetailService;
    private final OrderDetailRepository orderDetailRepository;
    private final OrderRepository orderRepository;
    private final IOrderService orderService;
    @GetMapping("")
    public ResponseEntity<?> getOrDeInfo ()
    {
        List<OrderDelivery> orderDeliveryList = orderDeliveryService.findAllOrDeStaffAdmin();
        List<OrderDelivery> orderDeliveryCancel = orderDeliveryService.findOrDeByCondition(OrDeCondition.REFUND);
        List<OrderDelivery> orderDeliveryOnGoing = orderDeliveryService.findOrDeByCondition(OrDeCondition.ONGOING);
        List<OrderDelivery> haveDeli = new ArrayList<>();

        for (OrderDelivery orderDelivery1 : orderDeliveryOnGoing) {
            long days = ChronoUnit.DAYS.between(orderDelivery1.getStart(), LocalDateTime.now());

            Deliverper dayper = orderDelivery1.getDeliverper();
            long devper;

            switch (dayper) {
                case every_day -> devper = 1;
                case two_day -> devper = 2;
                case three_day -> devper = 3;
                default -> devper = 1; // fallback an toàn
            }

            if (days % devper == 0 && orderDelivery1.getCondition() == OrDeCondition.ONGOING) {
                haveDeli.add(orderDelivery1);
            }
        }

        Map<String, Object> response = new HashMap<>();
        response.put("AllOrDe", orderDeliveryList);
        response.put("HaveDeli", haveDeli);
        response.put("CancelReq", orderDeliveryCancel);
        return ResponseEntity.ok(response);
    }

    @RequestMapping("/{id}/deli")
    public ResponseEntity<?> deliNow (@PathVariable int id){

        OrderDelivery orderDelivery1 = orderDeliveryService.findOrderDeliveryByAdmin(id);
        if (orderDelivery1.getCondition() == OrDeCondition.ONGOING)
        {
            Order order = new Order();
            order.setOrderDelivery(orderDelivery1);
            order.setTime(LocalDateTime.now());
            order.setPaid(IsPaid.Yes);
            order.setAccountID(orderDelivery1.getAccountID());
            order.setStatus(Status.ENABLE);
            order.setTotalAmount(
                    orderDelivery1.getTotal()
                            .divide(BigDecimal.valueOf(orderDelivery1.getOrderDeliveryType().getDays()), RoundingMode.HALF_UP)
            );
            order.setHadpaid(order.getTotalAmount());
            order.setDeliveryAddress(orderDelivery1.getAddress());
            order.setName(orderDelivery1.getName());
            order.setNote(orderDelivery1.getNote());
            order.setPhoneNumber(orderDelivery1.getPhoneNumber());
            order.setCondition(Condition.In_Transit);
            orderRepository.save(order);

            List<OrderDeliveryDetail> orderDeliveryDetailList = orderDeliveryDetailService.findOrDeDetailByOrDeID(id);
            for (OrderDeliveryDetail orderDeliveryDetail: orderDeliveryDetailList)
            {
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrderID(order);
                orderDetail.setStatus(Status.ENABLE);
                orderDetail.setQuantity(orderDeliveryDetail.getQuantity());
                orderDetail.setFlowerSize(orderDeliveryDetail.getFlowerSize());
                orderDetail.setPrice(orderDeliveryDetail.getFlowerSize().getPrice());
                orderDetail.setPaid(
                        orderDeliveryDetail.getFlowerSize().getPrice()
                                .multiply(BigDecimal.valueOf(orderDeliveryDetail.getQuantity()))
                );
                orderDetailRepository.save(orderDetail);
            }
            return ResponseEntity.ok("Order  created successfully");
        }
        else
            return ResponseEntity.badRequest().body("Order  created fail");
    }

//    @RequestMapping("/{id}/accept")
//    public ResponseEntity<?> acceptRequest (@PathVariable int id){
//        OrderDelivery orderDelivery1 = orderDeliveryService.findOrderDeliveryByAdmin(id);
//        if (orderDelivery1.getOrDeCondition() == OrDeCondition.CANCEL_REQUEST_IS_WAITING)
//        {
//            orderDelivery1.setOrDeCondition(OrDeCondition.REFUND);
//            List<Order> orderSuccess = orderService.findOrdersByOrDeIDAndCondition(id, Condition.Delivered_Successfully);
//            List<Order> orderFail = orderService.findOrdersByOrDeIDAndCondition(id, Condition.Return_to_shop);
//            List<Order> orderList = new ArrayList<>();
//            orderList.addAll(orderSuccess);
//            orderList.addAll(orderFail);
//        }
//    }
}
