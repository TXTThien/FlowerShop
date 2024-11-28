package org.example.controller.Staff;

import lombok.RequiredArgsConstructor;
import org.example.entity.Order;
import org.example.repository.OrderRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/staff/order")
@RequiredArgsConstructor
public class StaffOrderController {
    private final OrderRepository orderRepository;
    @GetMapping
    public ResponseEntity<List<Order>> getAllCategories() {
        List<Order> categories = orderRepository.findAll();
        return ResponseEntity.ok(categories);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Order> getCategoryById(@PathVariable Integer id) {
        Order category = orderRepository.findById(id).orElse(null);
        if (category != null) {
            return ResponseEntity.ok(category);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<Order> updateCategory(@PathVariable Integer id, @RequestBody Order categoryDetails) {
        Order commentType = orderRepository.findById(id).orElse(null);
        assert commentType != null;
        commentType.setStatus(categoryDetails.getStatus());
        commentType.setDate(categoryDetails.getDate());
        commentType.setPaid(categoryDetails.getPaid());
        commentType.setTotalAmount(categoryDetails.getTotalAmount());
        commentType.setDeliveryAddress(categoryDetails.getDeliveryAddress());
        commentType.setPhoneNumber(categoryDetails.getPhoneNumber());
        commentType.setName(categoryDetails.getName());
        commentType.setShipping(categoryDetails.getShipping());
        commentType.setNote(categoryDetails.getNote());
        commentType.setCondition(categoryDetails.getCondition());

        Order updatedCategory = orderRepository.save(commentType);
        return ResponseEntity.ok(updatedCategory);
    }

}
