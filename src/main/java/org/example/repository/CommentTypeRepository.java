package org.example.repository;

import org.example.entity.Cart;
import org.example.entity.CommentType;
import org.example.entity.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentTypeRepository extends JpaRepository<CommentType, Integer> {
    List<CommentType> findAllByStatus(Status status);
}
