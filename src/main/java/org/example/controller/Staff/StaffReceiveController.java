package org.example.controller.Staff;

import lombok.RequiredArgsConstructor;
import org.example.controller.NotificationController;
import org.example.dto.RepCommentDTO;
import org.example.entity.Account;
import org.example.entity.Comment;
import org.example.entity.Order;
import org.example.entity.RepComment;
import org.example.entity.enums.Stative;
import org.example.entity.enums.Status;
import org.example.repository.CommentRepository;
import org.example.repository.RepCommentRepository;
import org.example.service.IAccountService;
import org.example.service.ICommentService;
import org.example.service.securityService.GetIDAccountFromAuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/staff")
@RequiredArgsConstructor
public class StaffReceiveController {
    private final ICommentService commentService;
    private final CommentRepository commentRepository;
    private final RepCommentRepository repCommentRepository;
    private final NotificationController notificationController;
    private final GetIDAccountFromAuthService getIDAccountFromAuthService;
    private final IAccountService accountService;
    @GetMapping
    public ResponseEntity<?> getCommentWaiting() {
        List<Comment> categories = commentService.findCommentWaiting();

        Map<String, Object> response = new HashMap<>();
        response.put("comment", categories);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Comment> getDetailCommentWaiting(@PathVariable Integer id) {
        Comment categories = commentService.findCommentByID(id);
        return ResponseEntity.ok(categories);
    }
    @PostMapping("/{id}")
    public ResponseEntity<?> getResponseCommentWaiting(@PathVariable Integer id, @RequestBody RepCommentDTO repCommentDTO) {
        Comment categories = commentService.findCommentByID(id);
        int accountid = getIDAccountFromAuthService.common();
        Account account = accountService.getAccountById(accountid);
        if (categories.getStative() == Stative.Waiting)
        {
            categories.setStative(Stative.Processing);
            commentRepository.save(categories);
            RepComment repComment = new RepComment();
            repComment.setComment(categories);
            repComment.setAccount(account);
            repComment.setStatus(Status.ENABLE);
            repComment.setRepcommentdate(LocalDateTime.now());
            repComment.setImage(repCommentDTO.getImage());
            repComment.setRepcommenttext(repCommentDTO.getRepcommenttext());
            repCommentRepository.save(repComment);
            notificationController.commentRepNotification(repComment.getRepcommentID());
            return ResponseEntity.ok(categories);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Comment hien tai khong the tra loi");

    }
}
