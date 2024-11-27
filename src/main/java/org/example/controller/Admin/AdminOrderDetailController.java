package org.example.controller.Admin;

import lombok.RequiredArgsConstructor;
import org.example.entity.Order;
import org.example.entity.OrderDetail;
import org.example.entity.enums.Status;
import org.example.repository.OrderDetailRepository;
import org.example.repository.OrderRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/orderdetail")
@RequiredArgsConstructor
public class AdminOrderDetailController {
    private final OrderDetailRepository orderDetailRepository;
    @GetMapping
    public ResponseEntity<List<OrderDetail>> getAllCategories() {
        List<OrderDetail> categories = orderDetailRepository.findAll();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDetail> getCategoryById(@PathVariable Integer id) {
        OrderDetail category = orderDetailRepository.findById(id).orElse(null);
        if (category != null) {
            return ResponseEntity.ok(category);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<OrderDetail> createCategory(@RequestBody OrderDetail category) {
        OrderDetail createdCategory = orderDetailRepository.save(category);
        return ResponseEntity.ok(createdCategory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDetail> updateCategory(@PathVariable Integer id, @RequestBody OrderDetail categoryDetails) {
        OrderDetail commentType = orderDetailRepository.findById(id).orElse(null);
        assert commentType != null;
        commentType.setOrderID(categoryDetails.getOrderID());
        commentType.setStatus(categoryDetails.getStatus());
        commentType.setFlowerSize(categoryDetails.getFlowerSize());
        commentType.setPrice(categoryDetails.getPrice());
        commentType.setQuantity(categoryDetails.getQuantity());



        OrderDetail updatedCategory = orderDetailRepository.save(commentType);
        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("/softdelete/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Integer id) {
        OrderDetail category = orderDetailRepository.findById(id).orElse(null);
        if (category == null) {
            return ResponseEntity.notFound().build();
        }

        category.setStatus(Status.DISABLE);
        orderDetailRepository.save(category);

        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/harddelete/{id}")
    public ResponseEntity<Void> deleteBanner(@PathVariable Integer id) {
        OrderDetail category = orderDetailRepository.findById(id).orElse(null);
        if (category == null) {
            return ResponseEntity.notFound().build();
        }
        orderDetailRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
