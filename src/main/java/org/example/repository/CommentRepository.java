package org.example.repository;

import org.example.entity.Cart;
import org.example.entity.Comment;
import org.example.entity.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findCommentsByAccountID_AccountIDAndStatusOrderByCommentIDDesc(int id, Status status);
}
