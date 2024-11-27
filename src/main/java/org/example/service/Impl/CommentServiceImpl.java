package org.example.service.Impl;

import lombok.RequiredArgsConstructor;
import org.example.entity.Category;
import org.example.entity.Comment;
import org.example.entity.enums.Status;
import org.example.repository.CommentRepository;
import org.example.service.ICommentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements ICommentService {
    private final CommentRepository commentRepository;
    @Override
    public List<Comment> findCommentByAccountIDEnable(int idAccount) {
        return commentRepository.findCommentsByAccountID_AccountIDAndStatusOrderByCommentIDDesc(idAccount, Status.ENABLE);
    }

    @Override
    public Comment findCommentByID(int id) {
        return commentRepository.findCommentByCommentIDAndStatus(id, Status.ENABLE);
    }

    @Override
    public Comment updateComment(Integer id, Comment comment) {
        Comment existComment = commentRepository.findById(id).orElse(null);
        existComment.setCommentType(comment.getCommentType());
        existComment.setImage(comment.getImage());
        existComment.setStatus(comment.getStatus());
        existComment.setStative(comment.getStative());
        existComment.setText(comment.getText());
        existComment.setTitle(comment.getTitle());
        existComment.setDate(LocalDateTime.now());
        existComment.setAccountID(comment.getAccountID());
        return commentRepository.save(existComment);
    }

    @Override
    public Comment createComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    @Transactional
    public void harddelete(int id) {
        commentRepository.deleteById(id);
    }


}
