package org.example.controller.Admin;

import lombok.RequiredArgsConstructor;
import org.example.entity.OrderDetail;
import org.example.entity.Purpose;
import org.example.entity.enums.Status;
import org.example.repository.OrderDetailRepository;
import org.example.repository.PurposeRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/purpose")
@RequiredArgsConstructor
public class AdminPurposeController {
    private final PurposeRepository purposeRepository;
    @GetMapping
    public ResponseEntity<List<Purpose>> getAllCategories() {
        List<Purpose> categories = purposeRepository.findAll();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Purpose> getCategoryById(@PathVariable Integer id) {
        Purpose category = purposeRepository.findById(id).orElse(null);
        if (category != null) {
            return ResponseEntity.ok(category);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Purpose> createCategory(@RequestBody Purpose category) {
        Purpose createdCategory = purposeRepository.save(category);
        return ResponseEntity.ok(createdCategory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Purpose> updateCategory(@PathVariable Integer id, @RequestBody Purpose categoryDetails) {
        Purpose commentType = purposeRepository.findById(id).orElse(null);
        assert commentType != null;
        commentType.setPurposeName(categoryDetails.getPurposeName());
        commentType.setStatus(categoryDetails.getStatus());

        Purpose updatedCategory = purposeRepository.save(commentType);
        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("/softdelete/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Integer id) {
        Purpose category = purposeRepository.findById(id).orElse(null);
        if (category == null) {
            return ResponseEntity.notFound().build();
        }

        category.setStatus(Status.DISABLE);
        purposeRepository.save(category);

        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/harddelete/{id}")
    public ResponseEntity<Void> deleteBanner(@PathVariable Integer id) {
        Purpose category = purposeRepository.findById(id).orElse(null);
        if (category == null) {
            return ResponseEntity.notFound().build();
        }
        purposeRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
