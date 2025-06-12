package org.example.repository;

import org.example.entity.CommentType;
import org.example.entity.CustomDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomDetailRepository extends JpaRepository<CustomDetail, Integer> {
    List<CustomDetail> findCustomDetailsByCustomize_CustomID(int id);
}
