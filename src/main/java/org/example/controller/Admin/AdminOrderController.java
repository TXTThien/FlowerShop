package org.example.controller.Admin;

import lombok.RequiredArgsConstructor;
import org.example.entity.News;
import org.example.entity.Order;
import org.example.entity.enums.Status;
import org.example.repository.NewsRepository;
import org.example.repository.OrderRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/order")
@RequiredArgsConstructor
public class AdminOrderController {
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

    @PostMapping
    public ResponseEntity<Order> createCategory(@RequestBody Order category) {
        Order createdCategory = orderRepository.save(category);
        return ResponseEntity.ok(createdCategory);
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

    @DeleteMapping("/softdelete/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Integer id) {
        Order category = orderRepository.findById(id).orElse(null);
        if (category == null) {
            return ResponseEntity.notFound().build();
        }

        category.setStatus(Status.DISABLE);
        orderRepository.save(category);

        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/harddelete/{id}")
    public ResponseEntity<Void> deleteBanner(@PathVariable Integer id) {
        Order category = orderRepository.findById(id).orElse(null);
        if (category == null) {
            return ResponseEntity.notFound().build();
        }
        orderRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
