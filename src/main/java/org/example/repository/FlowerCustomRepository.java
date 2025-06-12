package org.example.repository;

import org.example.entity.CommentType;
import org.example.entity.FlowerCustom;
import org.example.entity.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlowerCustomRepository extends JpaRepository<FlowerCustom, Integer> {
    FlowerCustom findFlowerCustomByFlowerID(int id);
    List<FlowerCustom> findFlowerCustomsByStatus(Status status);
}
