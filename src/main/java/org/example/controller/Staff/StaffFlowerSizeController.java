package org.example.controller.Staff;

import lombok.RequiredArgsConstructor;
import org.example.entity.Flower;
import org.example.entity.FlowerSize;
import org.example.entity.enums.Status;
import org.example.repository.FlowerRepository;
import org.example.repository.FlowerSizeRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/staff/flowersize")
@RequiredArgsConstructor
public class StaffFlowerSizeController {
    private final FlowerSizeRepository flowerSizeRepository;
    private final FlowerRepository flowerRepository;

    @GetMapping
    public ResponseEntity<?> getAllCategories() {
        List<FlowerSize> categories = flowerSizeRepository.findAll();
        List<Flower> flowers = flowerRepository.findAll();

        Map<String, Object> response = new HashMap<>();
        response.put("flower", flowers);
        response.put("flowerSize", categories);
        return ResponseEntity.ok(response);
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
}
