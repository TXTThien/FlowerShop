package org.example.controller.User;

import lombok.RequiredArgsConstructor;
import org.example.dto.CommentDTO;
import org.example.dto.RepCommentDTO;
import org.example.entity.Account;
import org.example.entity.Comment;
import org.example.entity.CommentType;
import org.example.entity.RepComment;
import org.example.entity.enums.Stative;
import org.example.entity.enums.Status;
import org.example.repository.AccountRepository;
import org.example.repository.CommentRepository;
import org.example.service.ICommentService;
import org.example.service.ICommentTypeService;
import org.example.service.IRepCommentService;
import org.example.service.securityService.GetIDAccountFromAuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {
    private final ICommentService commentService;
    private final CommentRepository commentRepository;
    private final IRepCommentService repCommentService;
    private final ICommentTypeService commentTypeService;
    private final GetIDAccountFromAuthService getIDAccountService;
    private final AccountRepository accountRepository;

    @GetMapping("")
    public ResponseEntity<?> getCommentInfo() {
        int idAccount = getIDAccountService.common();
        Account account = accountRepository.findAccountByAccountID(idAccount);
        List<CommentType> commentTypes = commentTypeService.findAllEnable();
        List<Comment> comments = commentService.findCommentByAccountIDEnable(idAccount);

        Map<String, Object> response = new HashMap<>();
        response.put("commentTypes", commentTypes);
        response.put("comments", comments);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getRepCommentInfo(@PathVariable("id") int id) {
        int idAccount = getIDAccountService.common();
        Account account = accountRepository.findAccountByAccountID(idAccount);
        List<RepComment> repComments = repCommentService.findRepCommentByCommentID(id);
        Comment comment = commentService.findCommentByID(id);
        Map<String, Object> response = new HashMap<>();
        response.put("repComments", repComments);
        response.put("comment", comment);
        return ResponseEntity.ok(response);
    }
    @PostMapping("")
    public ResponseEntity<?> createComment(@RequestBody CommentDTO commentDTO) {
        int idAccount = getIDAccountService.common();
        Account account = accountRepository.findAccountByAccountID(idAccount);
        Comment comment = new Comment();
        CommentType commentType = commentTypeService.findTypebyID(commentDTO.getCommentType());
        comment.setCommentType(commentType);
        comment.setDate(LocalDateTime.now());
        comment.setImage(commentDTO.getImage());
        comment.setText(comment.getText());
        comment.setTitle(comment.getTitle());
        comment.setStative(Stative.Waiting);
        comment.setStatus(Status.ENABLE);
        comment.setAccountID(account);
        commentRepository.save(comment);
        return ResponseEntity.ok(comment);
    }
//    @PostMapping("/{id}")
//    public ResponseEntity<?> createRepComment(@RequestBody RepCommentDTO repCommentDTO) {
//        int idAccount = getIDAccountService.common();
//        Account account = accountRepository.findAccountByAccountID(idAccount);
//        Comment comment = new Comment();
//        CommentType commentType = commentTypeService.findTypebyID(commentDTO.getCommentType());
//        comment.setCommentType(commentType);
//        comment.setDate(LocalDateTime.now());
//        comment.setImage(commentDTO.getImage());
//        comment.setText(comment.getText());
//        comment.setTitle(comment.getTitle());
//        comment.setStative(Stative.Waiting);
//        comment.setStatus(Status.ENABLE);
//        comment.setAccountID(account);
//        commentRepository.save(comment);
//        return ResponseEntity.ok(comment);
//    }
}
