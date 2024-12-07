package org.example.controller.Staff;

import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.example.dto.ShipToOrderDTO;
import org.example.entity.Account;
import org.example.entity.Comment;
import org.example.entity.Order;
import org.example.entity.Shipping;
import org.example.entity.enums.Condition;
import org.example.entity.enums.Role;
import org.example.entity.enums.Status;
import org.example.repository.OrderRepository;
import org.example.repository.ShippingRepository;
import org.example.service.IAccountService;
import org.example.service.IOrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> getOkOrderCancelProcessing(@RequestBody Order orderid) {
        Order order = orderService.findOrderByOrderID(orderid.getOrderID());
        order.setCondition(Condition.Cancelled);
        orderService.update(order);
        return ResponseEntity.ok("Order cancel");
    }
    @PostMapping("/cancelprocessing/no")
    public ResponseEntity<?> getDeniedOrderCancelProcessing(@RequestBody Order orderid) {
        Order order = orderService.findOrderByOrderID(orderid.getOrderID());
        order.setCondition(Condition.Prepare);
        orderService.update(order);
        return ResponseEntity.ok("Order not cancel");
    }
}
