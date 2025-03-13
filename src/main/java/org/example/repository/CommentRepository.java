package org.example.repository;

import org.example.entity.Account;
import org.example.entity.Cart;
import org.example.entity.Comment;
import org.example.entity.enums.Stative;
import org.example.entity.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findCommentsByAccountID_AccountIDAndStatusOrderByCommentIDDesc(int id, Status status);
    Comment findCommentByCommentIDAndStatus(int id, Status status);
    List<Comment>findCommentsByStativeAndStatus(Stative stative, Status status);
    @Query("SELECT c FROM Comment c " +
            "JOIN c.repComments r " +
            "WHERE c.stative = 'Complete' " +
            "AND r.account.accountID = :id")
    List<Comment> findCommentsWithRepCommentByAccountId(@Param("id") Integer id);

    @Query("SELECT c FROM Comment c " +
            "JOIN c.repComments r " +
            "WHERE c.stative = 'Processing' " +
            "AND r.account.accountID = :id")
    List<Comment> findCommentsProcessingWithRepCommentByAccountId(@Param("id") Integer id);


}
