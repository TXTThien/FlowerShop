package org.example.controller.Staff;

import lombok.RequiredArgsConstructor;
import org.example.entity.OrderDetail;
import org.example.repository.OrderDetailRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/staff/orderdetail")
@RequiredArgsConstructor
public class StaffOrderDetailController {
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

}
