package org.example.controller.Admin;

import lombok.RequiredArgsConstructor;
import org.example.entity.Shipping;
import org.example.entity.Type;
import org.example.entity.enums.Status;
import org.example.repository.ShippingRepository;
import org.example.repository.TypeRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/type")
@RequiredArgsConstructor
public class AdminTypeController {
    private final TypeRepository typeRepository;
    @GetMapping
    public ResponseEntity<List<Type>> getAllCategories() {
        List<Type> categories = typeRepository.findAll();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Type> getCategoryById(@PathVariable Integer id) {
        Type category = typeRepository.findById(id).orElse(null);
        if (category != null) {
            return ResponseEntity.ok(category);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Type> createCategory(@RequestBody Type category) {
        Type createdCategory = typeRepository.save(category);
        return ResponseEntity.ok(createdCategory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Type> updateCategory(@PathVariable Integer id, @RequestBody Type categoryDetails) {
        Type commentType = typeRepository.findById(id).orElse(null);
        assert commentType != null;
        commentType.setMinConsume(categoryDetails.getMinConsume());
        commentType.setStatus(categoryDetails.getStatus());
        commentType.setTypeName(categoryDetails.getTypeName());


        Type updatedCategory = typeRepository.save(commentType);
        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("/softdelete/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Integer id) {
        Type category = typeRepository.findById(id).orElse(null);
        if (category == null) {
            return ResponseEntity.notFound().build();
        }

        category.setStatus(Status.DISABLE);
        typeRepository.save(category);

        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/harddelete/{id}")
    public ResponseEntity<Void> deleteBanner(@PathVariable Integer id) {
        Type category = typeRepository.findById(id).orElse(null);
        if (category == null) {
            return ResponseEntity.notFound().build();
        }
        typeRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
