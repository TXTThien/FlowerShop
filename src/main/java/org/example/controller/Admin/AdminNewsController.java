package org.example.controller.Admin;

import lombok.RequiredArgsConstructor;
import org.example.entity.FlowerSize;
import org.example.entity.News;
import org.example.entity.enums.Status;
import org.example.repository.FlowerSizeRepository;
import org.example.repository.NewsRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/news")
@RequiredArgsConstructor
public class AdminNewsController {
    private final NewsRepository newsRepository;

    @GetMapping
    public ResponseEntity<List<News>> getAllCategories() {
        List<News> categories = newsRepository.findAll();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<News> getCategoryById(@PathVariable Integer id) {
        News category = newsRepository.findById(id).orElse(null);
        if (category != null) {
            return ResponseEntity.ok(category);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<News> createCategory(@RequestBody News category) {
        News createdCategory = newsRepository.save(category);
        return ResponseEntity.ok(createdCategory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<News> updateCategory(@PathVariable Integer id, @RequestBody News categoryDetails) {
        News commentType = newsRepository.findById(id).orElse(null);
        assert commentType != null;
        commentType.setTitle(categoryDetails.getTitle());
        commentType.setStatus(categoryDetails.getStatus());
        commentType.setDate(categoryDetails.getDate());
        commentType.setImage(categoryDetails.getImage());
        commentType.setContent(categoryDetails.getContent());


        News updatedCategory = newsRepository.save(commentType);
        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("/softdelete/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Integer id) {
        News category = newsRepository.findById(id).orElse(null);
        if (category == null) {
            return ResponseEntity.notFound().build();
        }

        category.setStatus(Status.DISABLE);
        newsRepository.save(category);

        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/harddelete/{id}")
    public ResponseEntity<Void> deleteBanner(@PathVariable Integer id) {
        News category = newsRepository.findById(id).orElse(null);
        if (category == null) {
            return ResponseEntity.notFound().build();
        }
        newsRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
