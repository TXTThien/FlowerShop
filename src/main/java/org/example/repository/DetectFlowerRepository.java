package org.example.repository;

import org.example.entity.CommentType;
import org.example.entity.Detect;
import org.example.entity.DetectFlower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetectFlowerRepository extends JpaRepository<DetectFlower, Integer> {
    List<DetectFlower> findDetectFlowersByDetect(Detect detect);
}
