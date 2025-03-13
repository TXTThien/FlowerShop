package org.example.controller.Staff;

import lombok.RequiredArgsConstructor;
import org.example.controller.NotificationController;
import org.example.entity.Order;
import org.example.repository.OrderRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/staff/order")
@RequiredArgsConstructor
public class StaffOrderController {
    private final OrderRepository orderRepository;
    private final NotificationController notificationController;
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

        if (!Objects.equals(commentType.getPhoneNumber(), categoryDetails.getPhoneNumber()) || !Objects.equals(commentType.getDeliveryAddress(), categoryDetails.getDeliveryAddress()) || !Objects.equals(commentType.getName(), categoryDetails.getName()) || !Objects.equals(commentType.getNote(), categoryDetails.getNote()))
        {
            commentType.setDeliveryAddress(categoryDetails.getDeliveryAddress());
            commentType.setPhoneNumber(categoryDetails.getPhoneNumber());
            commentType.setName(categoryDetails.getName());
            commentType.setNote(categoryDetails.getNote());
            notificationController.orderInfomationNotification(commentType.getOrderID());
        }
        if (categoryDetails.getShipping() !=null){
            commentType.setShipping(categoryDetails.getShipping());
        }
        if (commentType.getCondition() != categoryDetails.getCondition())
        {
            commentType.setCondition(categoryDetails.getCondition());
            notificationController.orderConditionNotification(commentType.getOrderID());
        }

        Order updatedCategory = orderRepository.save(commentType);
        return ResponseEntity.ok(updatedCategory);
    }

}
