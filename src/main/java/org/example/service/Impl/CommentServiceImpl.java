package org.example.service.Impl;

import lombok.RequiredArgsConstructor;
import org.example.entity.Comment;
import org.example.entity.enums.Status;
import org.example.repository.CommentRepository;
import org.example.service.ICommentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements ICommentService {
    private final CommentRepository commentRepository;
    @Override
    public List<Comment> findCommentByAccountIDEnable(int idAccount) {
        return commentRepository.findCommentsByAccountID_AccountIDAndStatusOrderByCommentIDDesc(idAccount, Status.ENABLE);
    }
}
