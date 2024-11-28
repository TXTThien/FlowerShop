package org.example.controller.Shipper;

import lombok.RequiredArgsConstructor;
import org.example.entity.Order;
import org.example.repository.OrderRepository;
import org.example.service.IOrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/shipper")
@RequiredArgsConstructor
public class ShipperController {
    private final IOrderService orderService;
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> categories = orderService.findOrderNoShipping();
        return ResponseEntity.ok(categories);
    }
}
