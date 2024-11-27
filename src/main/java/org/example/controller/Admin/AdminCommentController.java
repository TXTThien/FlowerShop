//package org.example.controller.Admin;
//
//import lombok.RequiredArgsConstructor;
//import org.example.entity.Category;
//import org.example.entity.Comment;
//import org.example.entity.enums.Status;
//import org.example.repository.CommentRepository;
//import org.example.service.ICategoryService;
//import org.example.service.ICommentService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//@RestController
//@RequestMapping("/api/v1/admin/banner")
//@RequiredArgsConstructor
//public class AdminCommentController {
//    private final ICommentService commentService;
//    private final CommentRepository commentRepository;
//
//    @GetMapping
//    public ResponseEntity<List<Comment>> getAllCategories() {
//        List<Comment> comments = commentRepository.findAll();
//        return ResponseEntity.ok(comments);
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<Comment> getCategoryById(@PathVariable Integer id) {
//        Comment comment = commentRepository.findById(id).orElse(null);
//        if (comment != null) {
//            return ResponseEntity.ok(comment);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    @PostMapping
//    public ResponseEntity<Comment> createCategory(@RequestBody Comment comment) {
//        Comment createComment = commentService.createComment(comment);
//        return ResponseEntity.ok(createComment);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<Category> updateCategory(@PathVariable Integer id, @RequestBody Category categoryDetails) {
//        Category updatedCategory = categoryService.updateCategory(id, categoryDetails);
//        if (updatedCategory != null) {
//            return ResponseEntity.ok(updatedCategory);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    @DeleteMapping("/softdelete/{id}")
//    public ResponseEntity<Void> deleteCategory(@PathVariable Integer id) {
//        Category category = categoryService.getCategoryById(id);
//        if (category == null) {
//            return ResponseEntity.notFound().build();
//        }
//
//        category.setStatus(Status.DISABLE);
//        categoryService.updateCategory(id, category);
//
//        return ResponseEntity.noContent().build();
//    }
//    @DeleteMapping("/harddelete/{id}")
//    public ResponseEntity<Void> deleteBanner(@PathVariable Integer id) {
//        Category category = categoryService.getCategoryById(id);
//        if (category == null) {
//            return ResponseEntity.notFound().build();
//        }
//        categoryService.harddeleteAccount(id);
//        return ResponseEntity.noContent().build();
//    }
//}
