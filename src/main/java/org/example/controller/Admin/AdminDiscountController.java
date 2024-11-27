package org.example.controller.Admin;

import lombok.RequiredArgsConstructor;
import org.example.entity.CommentType;
import org.example.entity.Discount;
import org.example.entity.enums.Status;
import org.example.repository.CommentTypeRepository;
import org.example.repository.DiscountRepository;
import org.example.service.ICommentTypeService;
import org.example.service.IDiscountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/discount")
@RequiredArgsConstructor
public class AdminDiscountController {
    private final DiscountRepository discountRepository;

    @GetMapping
    public ResponseEntity<List<Discount>> getAllCategories() {
        List<Discount> categories = discountRepository.findAll();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Discount> getCategoryById(@PathVariable Integer id) {
        Discount category = discountRepository.findById(id).orElse(null);
        if (category != null) {
            return ResponseEntity.ok(category);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Discount> createCategory(@RequestBody Discount category) {
        Discount createdCategory = discountRepository.save(category);
        return ResponseEntity.ok(createdCategory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Discount> updateCategory(@PathVariable Integer id, @RequestBody Discount categoryDetails) {
        Discount commentType = discountRepository.findById(id).orElse(null);
        assert commentType != null;
        commentType.setCategoryID(categoryDetails.getCategoryID());
        commentType.setStatus(categoryDetails.getStatus());
        commentType.setType(categoryDetails.getType());
        commentType.setDiscountPercent(categoryDetails.getDiscountPercent());
        commentType.setEndDate(categoryDetails.getEndDate());
        commentType.setStartDate(categoryDetails.getStartDate());
        commentType.setType(categoryDetails.getType());

        Discount updatedCategory = discountRepository.save(commentType);
        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("/softdelete/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Integer id) {
        Discount category = discountRepository.findById(id).orElse(null);
        if (category == null) {
            return ResponseEntity.notFound().build();
        }

        category.setStatus(Status.DISABLE);
        discountRepository.save(category);

        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/harddelete/{id}")
    public ResponseEntity<Void> deleteBanner(@PathVariable Integer id) {
        Discount category = discountRepository.findById(id).orElse(null);
        if (category == null) {
            return ResponseEntity.notFound().build();
        }
        discountRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
