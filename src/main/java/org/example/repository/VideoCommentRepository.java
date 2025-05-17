package org.example.repository;

import org.example.entity.Type;
import org.example.entity.VideoComment;
import org.example.entity.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoCommentRepository extends JpaRepository<VideoComment, Integer> {
    List<VideoComment> findVideoCommentsByVideo_IdAndStatus(int id, Status status);
    List<VideoComment> findVideoCommentsByFatherComment_IdAndStatus(int id, Status status);
    VideoComment findVideoCommentByIdAndStatus(int id, Status status);
}
