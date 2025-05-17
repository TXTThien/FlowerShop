package org.example.repository;

import org.example.entity.Type;
import org.example.entity.Video;
import org.example.entity.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoRepository extends JpaRepository<Video, Integer> {
    Video findVideoByIdAndStatus(int id, Status status);
    List<Video> findVideosByStatus(Status status);
    List<Video> findVideosByAccountID_AccountIDAndStatus(int id, Status status);
}
