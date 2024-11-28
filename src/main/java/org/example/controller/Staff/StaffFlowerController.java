package org.example.controller.Staff;

import lombok.RequiredArgsConstructor;
import org.example.entity.Flower;
import org.example.entity.enums.Status;
import org.example.repository.FlowerRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/staff/flower")
@RequiredArgsConstructor
public class StaffFlowerController {
    private final FlowerRepository flowerRepository;

    @GetMapping
    public ResponseEntity<List<Flower>> getAllCategories() {
        List<Flower> categories = flowerRepository.findAll();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Flower> getCategoryById(@PathVariable Integer id) {
        Flower category = flowerRepository.findById(id).orElse(null);
        if (category != null) {
            return ResponseEntity.ok(category);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Flower> createCategory(@RequestBody Flower category) {
        Flower createdCategory = flowerRepository.save(category);
        return ResponseEntity.ok(createdCategory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Flower> updateCategory(@PathVariable Integer id, @RequestBody Flower categoryDetails) {
        Flower commentType = flowerRepository.findById(id).orElse(null);
        assert commentType != null;
        commentType.setName(categoryDetails.getName());
        commentType.setDescription(categoryDetails.getDescription());
        commentType.setImage(categoryDetails.getImage());
        commentType.setCategory(categoryDetails.getCategory());
        commentType.setLanguageOfFlowers(categoryDetails.getLanguageOfFlowers());
        commentType.setPurpose(categoryDetails.getPurpose());
        commentType.setStatus(categoryDetails.getStatus());

        Flower updatedCategory = flowerRepository.save(commentType);
        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("/softdelete/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Integer id) {
        Flower category = flowerRepository.findById(id).orElse(null);
        if (category == null) {
            return ResponseEntity.notFound().build();
        }

        category.setStatus(Status.DISABLE);
        flowerRepository.save(category);

        return ResponseEntity.noContent().build();
    }
}
