package org.example.controller.Staff;

import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.example.controller.NotificationController;
import org.example.dto.ShipToOrderDTO;
import org.example.entity.*;
import org.example.entity.enums.Condition;
import org.example.entity.enums.Role;
import org.example.entity.enums.Status;
import org.example.repository.FlowerSizeRepository;
import org.example.repository.OrderRepository;
import org.example.repository.ShippingRepository;
import org.example.service.IAccountService;
import org.example.service.IFlowerSizeService;
import org.example.service.IOrderDetailService;
import org.example.service.IOrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/staffmanager")
@RequiredArgsConstructor
public class StaffManageOrderController {
    private final IOrderService orderService;
    private final IAccountService accountService;
    private final ShippingRepository shippingRepository;
    private final IOrderDetailService orderDetailService;
    private final IFlowerSizeService flowerSizeService;
    private final FlowerSizeRepository flowerSizeRepository;
    private final NotificationController notificationController;
    @GetMapping("/ordernoship")
    public ResponseEntity<?> getOrderWaiting() {
        List<Order> orders = orderService.findOrderNoShipping();
        List<Account> accounts = accountService.getAccountByRole(Role.shipper);
        Map<String, Object> response = new HashMap<>();
        response.put("orders", orders);
        response.put("accounts", accounts);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/ordernoship/ship")
    public ResponseEntity<?> getOrderShip(@RequestBody ShipToOrderDTO shipToOrderDTO) {
        Shipping shipping = new Shipping();
        Account account = accountService.getAccountById(shipToOrderDTO.getAccountid());
        shipping.setStatus(Status.ENABLE);
        shipping.setAccountID(account);
        shipping.setStartDate(LocalDateTime.now());
        shippingRepository.save(shipping);
        Order order = orderService.findOrderByOrderID(shipToOrderDTO.getOrderid());
        order.setShipping(shipping);
        orderService.update(order);
        return ResponseEntity.ok("Shipping created and associated successfully");
    }

    @GetMapping("/cancelprocessing")
    public ResponseEntity<?> getOrderCancelProcessing() {
        List<Order> orders = orderService.findOrderByCondition(Condition.Cancel_is_Processing);
        Map<String, Object> response = new HashMap<>();
        response.put("orders", orders);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/cancelprocessing/yes")
    public ResponseEntity<?> getOkOrderCancelProcessing(@RequestBody Order requestOrder) {
        Order order = orderService.findOrderByOrderID(requestOrder.getOrderID());

        if (order == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found");
        }

        List<OrderDetail> orderDetails = orderDetailService.findOrderDetailByOrderID(requestOrder.getOrderID());

        if (orderDetails == null || orderDetails.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order details not found");
        }
        if (order.getHadpaid().equals(BigDecimal.ZERO)) {
            order.setCondition(Condition.Cancelled);
        } else {
            order.setCondition(Condition.Refund);
        }

        for (OrderDetail orderDetail : orderDetails) {
            FlowerSize flowerSize = flowerSizeService.findFlowerSizeByID(orderDetail.getFlowerSize().getFlowerSizeID());
            if (flowerSize != null) {
                flowerSize.setStock(flowerSize.getStock() + orderDetail.getQuantity());
                flowerSizeRepository.save(flowerSize);
            }
        }
        orderService.update(order);
        notificationController.orderConditionNotification(order.getOrderID());
        return ResponseEntity.ok("Order canceled successfully");
    }

    @PostMapping("/cancelprocessing/no")
    public ResponseEntity<?> getDeniedOrderCancelProcessing(@RequestBody Order orderid) {
        Order order = orderService.findOrderByOrderID(orderid.getOrderID());
        order.setCondition(Condition.In_Transit);
        orderService.update(order);
        notificationController.orderCancelFailNotification(order.getOrderID());
        return ResponseEntity.ok("Order not cancel");
    }
}
