package org.example.controller.Staff;

import lombok.RequiredArgsConstructor;
import org.example.auth.ChangePassword;
import org.example.controller.NotificationController;
import org.example.dto.CommentRepCommentDTO;
import org.example.dto.ListCommentDTO;
import org.example.dto.RepCommentDTO;
import org.example.entity.Account;
import org.example.entity.Comment;
import org.example.entity.Order;
import org.example.entity.RepComment;
import org.example.entity.enums.Stative;
import org.example.entity.enums.Status;
import org.example.repository.AccountRepository;
import org.example.repository.CommentRepository;
import org.example.repository.RepCommentRepository;
import org.example.service.IAccountService;
import org.example.service.ICommentService;
import org.example.service.securityService.GetIDAccountFromAuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/staffaccount")
@RequiredArgsConstructor
public class StaffAccountController {
    private final GetIDAccountFromAuthService getIDAccountFromAuthService;
    private final IAccountService accountService;
    private final AccountRepository accountRepository;
    private final RepCommentRepository repCommentRepository;
    private final CommentRepository commentRepository;
    private final PasswordEncoder passwordEncoder;
    private final ICommentService commentService;
    private final NotificationController notificationController;
    @GetMapping("")
    public ResponseEntity<Account> getAccountInfo() {

        int idAccount = getIDAccountFromAuthService.common();
        Account account = accountService.getAccountById(idAccount);
        if (account != null) {
            return ResponseEntity.ok(account);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @PutMapping("/updateinfo")
    public ResponseEntity<String> updateAccountByAccountID(
            @RequestBody Account updateAccountRequest) {
        int idAccount = getIDAccountFromAuthService.common();


        Account currentAccount = accountService.getAccountById(idAccount);

        if (currentAccount == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tài khoản không tồn tại.");
        }

        if (updateAccountRequest.getName() != null) {
            currentAccount.setName(updateAccountRequest.getName());
        }
        if (updateAccountRequest.getPhoneNumber() != null) {
            currentAccount.setPhoneNumber(updateAccountRequest.getPhoneNumber());
        }
        if (updateAccountRequest.getAddress() != null) {
            currentAccount.setAddress(updateAccountRequest.getAddress());
        }
        if (updateAccountRequest.getEmail() != null) {
            currentAccount.setEmail(updateAccountRequest.getEmail());
        }
        if (updateAccountRequest.getAvatar() != null) {
            currentAccount.setAvatar(updateAccountRequest.getAvatar());
        }
        accountService.updateAccountInfo(currentAccount);

        return ResponseEntity.ok("Cập nhật thông tin tài khoản thành công.");
    }

    @PutMapping("/changepassword")
    public ResponseEntity<String> updateChangePassword(
            @RequestBody ChangePassword changePassword) {

        int idAccount = getIDAccountFromAuthService.common();
        String newpass = changePassword.getNewpass();
        String curpass = changePassword.getCurpass();

        Account account = accountService.getAccountById(idAccount);

        if (!passwordEncoder.matches(curpass, account.getPassword())) {
            return ResponseEntity.badRequest().body("Mật khẩu hiện tại không đúng.");
        }

        String updatepass = passwordEncoder.encode(newpass);
        accountRepository.updatePassword(updatepass, idAccount);

        return ResponseEntity.ok("Đổi mật khẩu thành công.");
    }
    @GetMapping("/commentcomplete")
    public ResponseEntity<?> getListCommentComplete(){
        int id = getIDAccountFromAuthService.common();
        List<Comment> orderList = commentService.findCommentComplete(id);
        List<ListCommentDTO> listCommentDTOS = new ArrayList<>();
        for (Comment comment : orderList) {
            ListCommentDTO commentDTO = new ListCommentDTO();
            commentDTO.setCommentID(comment.getCommentID());
            commentDTO.setCommentType(comment.getCommentType());
            commentDTO.setTitle(comment.getTitle());
            commentDTO.setText(comment.getText());
            commentDTO.setDate(comment.getDate());
            commentDTO.setStative(comment.getStative());
            List<RepComment> repComments = repCommentRepository.findRepCommentByComment_CommentIDAndStatus(comment.getCommentID(), Status.ENABLE);
            commentDTO.setNumberRep(repComments.size());
            listCommentDTOS.add(commentDTO);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("comment", listCommentDTOS);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/commentprocess")
    public ResponseEntity<?> getListCommentProcess(){
        int id = getIDAccountFromAuthService.common();
        List<Comment> orderList = commentService.findCommentProcess(id);
        List<ListCommentDTO> listCommentDTOS = new ArrayList<>();
        for (Comment comment : orderList) {
            ListCommentDTO commentDTO = new ListCommentDTO();
            commentDTO.setCommentID(comment.getCommentID());
            commentDTO.setCommentType(comment.getCommentType());
            commentDTO.setTitle(comment.getTitle());
            commentDTO.setText(comment.getText());
            commentDTO.setDate(comment.getDate());
            commentDTO.setStative(comment.getStative());
            List<RepComment> repComments = repCommentRepository.findRepCommentByComment_CommentIDAndStatus(comment.getCommentID(), Status.ENABLE);
            commentDTO.setNumberRep(repComments.size());
            listCommentDTOS.add(commentDTO);
        }
        Map<String, Object> response = new HashMap<>();
        response.put("comment", listCommentDTOS);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/comment/{id}")
    public ResponseEntity<?> getRepCommentInfo(@PathVariable("id") int id) {
        Comment comment = commentService.findCommentByID(id);
        CommentRepCommentDTO commentDTO = new CommentRepCommentDTO();
        commentDTO.setCommentID(comment.getCommentID());
        commentDTO.setCommentTitle(comment.getTitle());
        commentDTO.setCommentText(comment.getText());
        commentDTO.setCommentDate(comment.getDate());
        commentDTO.setCommentStatus(comment.getStatus().toString());
        commentDTO.setCommentStative(comment.getStative().toString());
        commentDTO.setCommentType(comment.getCommentType().getCommenttypename());
        commentDTO.setImage(comment.getImage());
        List<CommentRepCommentDTO.RepCommentDTO> repCommentDTOList = comment.getRepComments().stream()
                .map(repComment -> new CommentRepCommentDTO.RepCommentDTO(
                        repComment.getRepcommentID(),
                        repComment.getAccount().getAccountID(),
                        repComment.getAccount().getName(),
                        repComment.getAccount().getAvatar(),
                        repComment.getRepcommentdate(),
                        repComment.getRepcommenttext(),
                        repComment.getStatus().toString(),
                        repComment.getImage()))
                .collect(Collectors.toList());
        commentDTO.setRepComments(repCommentDTOList);
        Map<String, Object> response = new HashMap<>();
        response.put("commentDTO", commentDTO);

        return ResponseEntity.ok(response);
    }
    @PostMapping("/commentprocess/{commentid}")
    public ResponseEntity<?> postRepCommentProcess(@PathVariable int commentid, @RequestBody RepCommentDTO repCommentDTO){
        Comment comment = commentService.findCommentByID(commentid);
        int accountid = getIDAccountFromAuthService.common();
        Account account = accountService.getAccountById(accountid);
        if (comment.getStative() == Stative.Processing)
        {
            RepComment repComment = new RepComment();
            repComment.setComment(comment);
            repComment.setAccount(account);
            repComment.setStatus(Status.ENABLE);
            repComment.setRepcommentdate(LocalDateTime.now());
            repComment.setImage(repCommentDTO.getImage());
            repComment.setRepcommenttext(repCommentDTO.getRepcommenttext());
            repCommentRepository.save(repComment);
            Map<String, Object> response = new HashMap<>();
            response.put("repComment", repComment);
            notificationController.commentRepNotification(repComment.getRepcommentID());
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Comment hien tai khong the tra loi");
    }
    @RequestMapping("/commentprocess/{commentid}/complete")
    public ResponseEntity<?> CompleteRepCommentProcess(@PathVariable int commentid){
        Comment comment = commentService.findCommentByID(commentid);

        comment.setStative(Stative.Complete);
        commentRepository.save(comment);
        Map<String, Object> response = new HashMap<>();
        response.put("repComment", comment);
        notificationController.commentCompleteNotification(comment.getCommentID());

        return ResponseEntity.ok(response);
    }
}
