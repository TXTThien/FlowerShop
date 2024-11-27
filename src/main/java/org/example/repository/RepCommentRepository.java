package org.example.repository;

import org.example.entity.Purpose;
import org.example.entity.RepComment;
import org.example.entity.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepCommentRepository extends JpaRepository<RepComment, Integer> {
    List<RepComment> findRepCommentByComment_CommentIDAndStatus(int id, Status status);
}
