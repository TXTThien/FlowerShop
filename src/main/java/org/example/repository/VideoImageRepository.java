package org.example.repository;

import org.example.entity.Type;
import org.example.entity.VideoImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoImageRepository extends JpaRepository<VideoImage, Integer> {
    List<VideoImage> findVideoImagesByVideoComment_Id(int id);
}
