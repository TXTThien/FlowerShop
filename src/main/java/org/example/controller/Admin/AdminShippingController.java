package org.example.controller.Admin;

import lombok.RequiredArgsConstructor;
import org.example.entity.Review;
import org.example.entity.Shipping;
import org.example.entity.enums.Status;
import org.example.repository.ReviewRepository;
import org.example.repository.ShippingRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/shipping")
@RequiredArgsConstructor
public class AdminShippingController {
    private final ShippingRepository shippingRepository;
    @GetMapping
    public ResponseEntity<List<Shipping>> getAllCategories() {
        List<Shipping> categories = shippingRepository.findAll();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Shipping> getCategoryById(@PathVariable Integer id) {
        Shipping category = shippingRepository.findById(id).orElse(null);
        if (category != null) {
            return ResponseEntity.ok(category);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Shipping> createCategory(@RequestBody Shipping category) {
        Shipping createdCategory = shippingRepository.save(category);
        return ResponseEntity.ok(createdCategory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Shipping> updateCategory(@PathVariable Integer id, @RequestBody Shipping categoryDetails) {
        Shipping commentType = shippingRepository.findById(id).orElse(null);
        assert commentType != null;
        commentType.setStartDate(categoryDetails.getStartDate());
        commentType.setStatus(categoryDetails.getStatus());
        commentType.setCompleteDate(categoryDetails.getCompleteDate());
        commentType.setNote(categoryDetails.getNote());
        commentType.setAccountID(categoryDetails.getAccountID());

        Shipping updatedCategory = shippingRepository.save(commentType);
        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("/softdelete/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Integer id) {
        Shipping category = shippingRepository.findById(id).orElse(null);
        if (category == null) {
            return ResponseEntity.notFound().build();
        }

        category.setStatus(Status.DISABLE);
        shippingRepository.save(category);

        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/harddelete/{id}")
    public ResponseEntity<Void> deleteBanner(@PathVariable Integer id) {
        Shipping category = shippingRepository.findById(id).orElse(null);
        if (category == null) {
            return ResponseEntity.notFound().build();
        }
        shippingRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}