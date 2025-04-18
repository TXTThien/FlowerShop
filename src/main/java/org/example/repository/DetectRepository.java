package org.example.repository;

import org.example.entity.CommentType;
import org.example.entity.Detect;
import org.example.entity.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetectRepository extends JpaRepository<Detect, Integer> {
    Detect findDetectByFlowerdetectAndStatus(String name , Status status);
}
