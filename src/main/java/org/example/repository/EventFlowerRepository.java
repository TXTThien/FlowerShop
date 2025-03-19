package org.example.repository;

import org.example.entity.Discount;
import org.example.entity.EventFlower;
import org.example.entity.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventFlowerRepository extends JpaRepository<EventFlower, Integer> {
    EventFlower findEventFlowerByFlowerSize_FlowerSizeIDAndStatus(int id, Status status);
}
