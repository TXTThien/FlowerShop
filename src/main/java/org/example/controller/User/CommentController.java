package org.example.controller.User;

import lombok.RequiredArgsConstructor;
import org.example.entity.Account;
import org.example.entity.Comment;
import org.example.entity.CommentType;
import org.example.entity.enums.Status;
import org.example.repository.AccountRepository;
import org.example.service.ICommentService;
import org.example.service.ICommentTypeService;
import org.example.service.securityService.GetIDAccountFromAuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {
    private final ICommentService commentService;
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
}
