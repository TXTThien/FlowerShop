package org.example.repository;

import org.example.entity.CommentType;
import org.example.entity.Detect;
import org.example.entity.enums.Status;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetectRepository extends JpaRepository<Detect, Integer> {
    Detect findDetectByFlowerdetectAndStatus(String name , Status status);
    Detect findDetectById(int id);

    @Query("SELECT d FROM Detect d " +
            "WHERE (:name IS NULL OR TRIM(LOWER(d.vietnamname)) LIKE CONCAT('%', TRIM(LOWER(:name)), '%')) " +
            "AND d.status = :status " +
            "ORDER BY CASE WHEN TRIM(LOWER(d.vietnamname)) = TRIM(LOWER(:name)) THEN 0 ELSE 1 END")
    List<Detect> findTopByVietnamname(@Param("name") String name, @Param("status") Status status, Pageable pageable);



}
