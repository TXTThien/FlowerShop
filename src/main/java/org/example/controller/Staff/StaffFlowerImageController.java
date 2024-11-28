package org.example.controller.Staff;

import lombok.RequiredArgsConstructor;
import org.example.entity.FlowerImages;
import org.example.entity.enums.Status;
import org.example.repository.FlowerImagesRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/staff/flowerimage")
@RequiredArgsConstructor
public class StaffFlowerImageController {
    private final FlowerImagesRepository flowerImagesRepository;

    @GetMapping
    public ResponseEntity<List<FlowerImages>> getAllCategories() {
        List<FlowerImages> categories = flowerImagesRepository.findAll();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlowerImages> getCategoryById(@PathVariable Integer id) {
        FlowerImages category = flowerImagesRepository.findById(id).orElse(null);
        if (category != null) {
            return ResponseEntity.ok(category);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<FlowerImages> createCategory(@RequestBody FlowerImages category) {
        FlowerImages createdCategory = flowerImagesRepository.save(category);
        return ResponseEntity.ok(createdCategory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FlowerImages> updateCategory(@PathVariable Integer id, @RequestBody FlowerImages categoryDetails) {
        FlowerImages commentType = flowerImagesRepository.findById(id).orElse(null);
        assert commentType != null;
        commentType.setFlower(categoryDetails.getFlower());
        commentType.setStatus(categoryDetails.getStatus());
        commentType.setImageURL(categoryDetails.getImageURL());
        commentType.setStatus(categoryDetails.getStatus());

        FlowerImages updatedCategory = flowerImagesRepository.save(commentType);
        return ResponseEntity.ok(updatedCategory);
    }
    @DeleteMapping("/softdelete/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Integer id) {
        FlowerImages category = flowerImagesRepository.findById(id).orElse(null);
        if (category == null) {
            return ResponseEntity.notFound().build();
        }

        category.setStatus(Status.DISABLE);
        flowerImagesRepository.save(category);

        return ResponseEntity.noContent().build();
    }

}
