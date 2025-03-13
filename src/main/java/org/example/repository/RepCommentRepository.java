package org.example.repository;

import org.example.entity.Account;
import org.example.entity.Purpose;
import org.example.entity.RepComment;
import org.example.entity.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepCommentRepository extends JpaRepository<RepComment, Integer> {
    List<RepComment> findRepCommentByComment_CommentIDAndStatus(int id, Status status);

    RepComment findRepCommentByRepcommentIDAndStatus(int id, Status status);

    @Query("SELECT DISTINCT rc.account FROM RepComment rc " +
            "WHERE rc.comment.commentID = :commentId " +
            "AND rc.account.accountID <> (SELECT c.accountID.accountID FROM Comment c WHERE c.commentID = :commentId)")
    Account findReplyAccountExcludingCommentOwner(int commentId);
}
