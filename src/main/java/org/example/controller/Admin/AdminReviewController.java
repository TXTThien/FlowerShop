package org.example.controller.Admin;

import lombok.RequiredArgsConstructor;
import org.example.entity.*;
import org.example.entity.enums.Status;
import org.example.repository.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin/review")
@RequiredArgsConstructor
public class AdminReviewController {
    private final ReviewRepository reviewRepository;
    private final AccountRepository accountRepository;
    private final FlowerRepository flowerRepository;
    @GetMapping
    public ResponseEntity<?> getAllCategories() {
        List<Review> reviews = reviewRepository.findAll();
        List<Account> accounts  = accountRepository.findAll();
        List<Flower> flowers = flowerRepository.findAll();

        Map<String, Object> response = new HashMap<>();
        response.put("reviews", reviews);
        response.put("accounts", accounts);
        response.put("flowers", flowers);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Review> getCategoryById(@PathVariable Integer id) {
        Review category = reviewRepository.findById(id).orElse(null);
        if (category != null) {
            return ResponseEntity.ok(category);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Review> createCategory(@RequestBody Review category) {
        Review createdCategory = reviewRepository.save(category);
        return ResponseEntity.ok(createdCategory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Review> updateCategory(@PathVariable Integer id, @RequestBody Review categoryDetails) {
        Review commentType = reviewRepository.findById(id).orElse(null);
        assert commentType != null;
        commentType.setFlower(categoryDetails.getFlower());
        commentType.setStatus(categoryDetails.getStatus());
        commentType.setImage(categoryDetails.getImage());
        commentType.setComment(categoryDetails.getComment());
        commentType.setDate(categoryDetails.getDate());
        commentType.setAccountID(categoryDetails.getAccountID());
        commentType.setRating(categoryDetails.getRating());

        Review updatedCategory = reviewRepository.save(commentType);
        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("/softdelete/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Integer id) {
        Review category = reviewRepository.findById(id).orElse(null);
        if (category == null) {
            return ResponseEntity.notFound().build();
        }

        category.setStatus(Status.DISABLE);
        reviewRepository.save(category);

        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/harddelete/{id}")
    public ResponseEntity<Void> deleteBanner(@PathVariable Integer id) {
        Review category = reviewRepository.findById(id).orElse(null);
        if (category == null) {
            return ResponseEntity.notFound().build();
        }
        reviewRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
