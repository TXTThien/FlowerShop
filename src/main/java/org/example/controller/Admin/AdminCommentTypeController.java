package org.example.controller.Admin;

import lombok.RequiredArgsConstructor;
import org.example.entity.Category;
import org.example.entity.CommentType;
import org.example.entity.enums.Status;
import org.example.repository.CommentRepository;
import org.example.repository.CommentTypeRepository;
import org.example.service.ICommentService;
import org.example.service.ICommentTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/commenttype")
@RequiredArgsConstructor
public class AdminCommentTypeController {
    private final ICommentTypeService commentTypeService;
    private final CommentTypeRepository commentTypeRepository;
    @GetMapping
    public ResponseEntity<List<CommentType>> getAllCategories() {
        List<CommentType> categories = commentTypeRepository.findAll();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentType> getCategoryById(@PathVariable Integer id) {
        CommentType category = commentTypeService.findTypebyID(id);
        if (category != null) {
            return ResponseEntity.ok(category);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<CommentType> createCategory(@RequestBody CommentType category) {
        CommentType createdCategory = commentTypeRepository.save(category);
        return ResponseEntity.ok(createdCategory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentType> updateCategory(@PathVariable Integer id, @RequestBody CommentType categoryDetails) {
        CommentType commentType = commentTypeService.findTypebyID(id);
        commentType.setCommenttypename(categoryDetails.getCommenttypename());
        commentType.setStatus(categoryDetails.getStatus());
        CommentType updatedCategory = commentTypeRepository.save(commentType);
        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("/softdelete/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Integer id) {
        CommentType category = commentTypeService.findTypebyID(id);
        if (category == null) {
            return ResponseEntity.notFound().build();
        }

        category.setStatus(Status.DISABLE);
        commentTypeRepository.save(category);

        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/harddelete/{id}")
    public ResponseEntity<Void> deleteBanner(@PathVariable Integer id) {
        CommentType category = commentTypeService.findTypebyID(id);
        if (category == null) {
            return ResponseEntity.notFound().build();
        }
        commentTypeRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
