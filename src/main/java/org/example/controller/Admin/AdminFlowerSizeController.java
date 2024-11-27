package org.example.controller.Admin;

import lombok.RequiredArgsConstructor;
import org.example.entity.FlowerImages;
import org.example.entity.FlowerSize;
import org.example.entity.enums.Status;
import org.example.repository.FlowerImagesRepository;
import org.example.repository.FlowerSizeRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/flowersize")
@RequiredArgsConstructor
public class AdminFlowerSizeController {
    private final FlowerSizeRepository flowerSizeRepository;

    @GetMapping
    public ResponseEntity<List<FlowerSize>> getAllCategories() {
        List<FlowerSize> categories = flowerSizeRepository.findAll();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlowerSize> getCategoryById(@PathVariable Integer id) {
        FlowerSize category = flowerSizeRepository.findById(id).orElse(null);
        if (category != null) {
            return ResponseEntity.ok(category);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<FlowerSize> createCategory(@RequestBody FlowerSize category) {
        FlowerSize createdCategory = flowerSizeRepository.save(category);
        return ResponseEntity.ok(createdCategory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FlowerSize> updateCategory(@PathVariable Integer id, @RequestBody FlowerSize categoryDetails) {
        FlowerSize commentType = flowerSizeRepository.findById(id).orElse(null);
        assert commentType != null;
        commentType.setFlower(categoryDetails.getFlower());
        commentType.setStatus(categoryDetails.getStatus());
        commentType.setSizeName(categoryDetails.getSizeName());
        commentType.setLength(categoryDetails.getLength());
        commentType.setHigh(categoryDetails.getHigh());
        commentType.setWeight(categoryDetails.getWeight());
        commentType.setWidth(categoryDetails.getWidth());
        commentType.setStock(categoryDetails.getStock());
        commentType.setCost(categoryDetails.getCost());
        commentType.setPrice(categoryDetails.getPrice());

        FlowerSize updatedCategory = flowerSizeRepository.save(commentType);
        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("/softdelete/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Integer id) {
        FlowerSize category = flowerSizeRepository.findById(id).orElse(null);
        if (category == null) {
            return ResponseEntity.notFound().build();
        }

        category.setStatus(Status.DISABLE);
        flowerSizeRepository.save(category);

        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/harddelete/{id}")
    public ResponseEntity<Void> deleteBanner(@PathVariable Integer id) {
        FlowerSize category = flowerSizeRepository.findById(id).orElse(null);
        if (category == null) {
            return ResponseEntity.notFound().build();
        }
        flowerSizeRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
