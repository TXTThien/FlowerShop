package org.example.repository;

import org.example.entity.FlowerImages;
import org.example.entity.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlowerImagesRepository extends JpaRepository<FlowerImages, Integer> {
    List<FlowerImages> findFlowerImagesByFlowerFlowerIDAndStatus(int id, Status status);

}
