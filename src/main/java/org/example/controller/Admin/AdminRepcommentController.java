package org.example.controller.Admin;

import lombok.RequiredArgsConstructor;
import org.example.entity.Account;
import org.example.entity.Comment;
import org.example.entity.Purpose;
import org.example.entity.RepComment;
import org.example.entity.enums.Status;
import org.example.repository.AccountRepository;
import org.example.repository.CommentRepository;
import org.example.repository.PurposeRepository;
import org.example.repository.RepCommentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin/repcomment")
@RequiredArgsConstructor
public class AdminRepcommentController {
    private final RepCommentRepository repCommentRepository;
    private final AccountRepository accountRepository;
    private final CommentRepository commentRepository;

    @GetMapping
    public ResponseEntity<?> getAllCategories() {
        List<RepComment> repComments = repCommentRepository.findAll();
        List<Account> accounts  = accountRepository.findAll();
        List<Comment> comments = commentRepository.findAll();

        Map<String, Object> response = new HashMap<>();
        response.put("repComments", repComments);
        response.put("accounts", accounts);
        response.put("comments", comments);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RepComment> getCategoryById(@PathVariable Integer id) {
        RepComment category = repCommentRepository.findById(id).orElse(null);
        if (category != null) {
            return ResponseEntity.ok(category);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<RepComment> createCategory(@RequestBody RepComment category) {
        RepComment createdCategory = repCommentRepository.save(category);
        return ResponseEntity.ok(createdCategory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RepComment> updateCategory(@PathVariable Integer id, @RequestBody RepComment categoryDetails) {
        RepComment commentType = repCommentRepository.findById(id).orElse(null);
        assert commentType != null;
        commentType.setComment(categoryDetails.getComment());
        commentType.setStatus(categoryDetails.getStatus());
        commentType.setImage(categoryDetails.getImage());
        commentType.setRepcommenttext(categoryDetails.getRepcommenttext());
        commentType.setRepcommentdate(categoryDetails.getRepcommentdate());
        commentType.setAccount(categoryDetails.getAccount());

        RepComment updatedCategory = repCommentRepository.save(commentType);
        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("/softdelete/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Integer id) {
        RepComment category = repCommentRepository.findById(id).orElse(null);
        if (category == null) {
            return ResponseEntity.notFound().build();
        }

        category.setStatus(Status.DISABLE);
        repCommentRepository.save(category);

        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/harddelete/{id}")
    public ResponseEntity<Void> deleteBanner(@PathVariable Integer id) {
        RepComment category = repCommentRepository.findById(id).orElse(null);
        if (category == null) {
            return ResponseEntity.notFound().build();
        }
        repCommentRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
