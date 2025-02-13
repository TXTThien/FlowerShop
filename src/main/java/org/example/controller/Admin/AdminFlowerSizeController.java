package org.example.controller.Admin;

import lombok.RequiredArgsConstructor;
import org.example.entity.Flower;
import org.example.entity.FlowerImages;
import org.example.entity.FlowerSize;
import org.example.entity.enums.Preorderable;
import org.example.entity.enums.Status;
import org.example.repository.FlowerImagesRepository;
import org.example.repository.FlowerRepository;
import org.example.repository.FlowerSizeRepository;
import org.example.service.IFlowerSizeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin/flowersize")
@RequiredArgsConstructor
public class AdminFlowerSizeController {
    private final FlowerSizeRepository flowerSizeRepository;
    private final FlowerRepository flowerRepository;
    private final IFlowerSizeService flowerSizeService;
    @GetMapping
    public ResponseEntity<?> getAllCategories() {
        List<FlowerSize> flowerSizes = flowerSizeRepository.findAll();
        List<Flower> flowers = flowerRepository.findAll();

        Map<String, Object> response = new HashMap<>();
        response.put("flowers", flowers);
        response.put("flowerSizes", flowerSizes);
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
        commentType.setPreorderable(categoryDetails.getPreorderable());
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
    @RequestMapping("/preorderable")
    public void Preorderable (){
        List<FlowerSize> flowerSizes = flowerSizeRepository.findAll();
        int count = 0;
        for (FlowerSize flowerSize : flowerSizes) {
            if (flowerSize.getPreorderable() == Preorderable.NO)
                count++;
        }
        if (count == flowerSizes.size())
        {
            for (FlowerSize flowerSize : flowerSizes) {
                flowerSize.setPreorderable(Preorderable.YES);
                flowerSizeRepository.save(flowerSize);
            }
        }
        else
        {
            for (FlowerSize flowerSize : flowerSizes) {
                flowerSize.setPreorderable(Preorderable.NO);
                flowerSizeRepository.save(flowerSize);
            }
        }
    }

    @RequestMapping("/preorderable/{id}")
    public void PreorderableID(@PathVariable int id){
        FlowerSize flowerSize = flowerSizeService.findFlowerSizeByID(id);
        if (flowerSize.getPreorderable()==Preorderable.NO)
            flowerSize.setPreorderable(Preorderable.YES);
        else
            flowerSize.setPreorderable(Preorderable.NO);
        flowerSizeRepository.save(flowerSize);
    }
}
