package org.example.controller.Staff;

import lombok.RequiredArgsConstructor;
import org.example.entity.Account;
import org.example.entity.Comment;
import org.example.entity.Order;
import org.example.entity.Shipping;
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
@RequestMapping("/staff")
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
    @RequestMapping("/ordernoship/ship")
    public ResponseEntity<?> getOrderShip(@RequestBody Order orderid, @RequestBody Account accountid) {
        Shipping shipping = new Shipping();
        shipping.setStatus(Status.ENABLE);
        shipping.setAccountID(accountid);
        shipping.setStartDate(LocalDateTime.now());
        shippingRepository.save(shipping);
        Order order = orderService.findOrderByOrderID(orderid.getOrderID());
        order.setShipping(shipping);
        orderService.update(order);
        return ResponseEntity.ok("Shipping created and associated successfully");
    }
}
