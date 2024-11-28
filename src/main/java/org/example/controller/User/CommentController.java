package org.example.controller.User;

import lombok.RequiredArgsConstructor;
import org.example.dto.CommentDTO;
import org.example.dto.CommentRepCommentDTO;
import org.example.dto.RepCommentDTO;
import org.example.entity.Account;
import org.example.entity.Comment;
import org.example.entity.CommentType;
import org.example.entity.RepComment;
import org.example.entity.enums.Stative;
import org.example.entity.enums.Status;
import org.example.repository.AccountRepository;
import org.example.repository.CommentRepository;
import org.example.repository.RepCommentRepository;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {
    private final ICommentService commentService;
    private final CommentRepository commentRepository;
    private final RepCommentRepository repCommentRepository;
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
        Comment comment = commentService.findCommentByID(id);
        CommentRepCommentDTO commentDTO = new CommentRepCommentDTO();
        commentDTO.setCommentID(comment.getCommentID());
        commentDTO.setCommentTitle(comment.getTitle());
        commentDTO.setCommentText(comment.getText());
        commentDTO.setCommentDate(comment.getDate());
        commentDTO.setCommentStatus(comment.getStatus().toString());
        commentDTO.setCommentStative(comment.getStative().toString());
        commentDTO.setImage(comment.getImage());
        List<CommentRepCommentDTO.RepCommentDTO> repCommentDTOList = comment.getRepComments().stream()
                .map(repComment -> new CommentRepCommentDTO.RepCommentDTO(
                        repComment.getRepcommentID(),
                        repComment.getAccount().getAccountID(),  // Giả sử AccountID là ID của tài khoản
                        repComment.getAccount().getName(),      // Giả sử bạn muốn trả về tên tài khoản
                        repComment.getRepcommentdate(),
                        repComment.getRepcommenttext(),
                        repComment.getStatus().toString(),
                        repComment.getImage()))
                .collect(Collectors.toList());
        commentDTO.setRepComments(repCommentDTOList);
        return ResponseEntity.ok(commentDTO);
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
        comment.setText(commentDTO.getText());
        comment.setTitle(commentDTO.getTitle());
        comment.setStative(Stative.Waiting);
        comment.setStatus(Status.ENABLE);
        comment.setAccountID(account);
        commentRepository.save(comment);
        return ResponseEntity.ok(comment);
    }
    @PostMapping("/{id}")
    public ResponseEntity<?> createRepComment(@PathVariable("id") int id, @RequestBody RepCommentDTO repCommentDTO) {
        int idAccount = getIDAccountService.common();
        Account account = accountRepository.findAccountByAccountID(idAccount);
        Comment comment = commentService.findCommentByID(id);
        if (comment.getStative() == Stative.Processing){
            RepComment repComment = new RepComment();
            repComment.setComment(comment);
            repComment.setAccount(account);
            repComment.setRepcommentdate(LocalDateTime.now());
            repComment.setStatus(Status.ENABLE);
            repComment.setImage(repCommentDTO.getImage());
            repComment.setRepcommenttext(repCommentDTO.getRepcommenttext());
            repCommentRepository.save(repComment);
            return ResponseEntity.ok(repComment);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Comment đang chờ xử lý.");
    }
}
