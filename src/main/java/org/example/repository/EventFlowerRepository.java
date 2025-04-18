package org.example.repository;

import org.example.entity.Discount;
import org.example.entity.EventFlower;
import org.example.entity.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventFlowerRepository extends JpaRepository<EventFlower, Integer> {
    EventFlower findEventFlowerByFlowerSize_FlowerSizeIDAndStatus(int id, Status status);
    List<EventFlower> findEventFlowerByEvent_IdAndStatus(int id, Status status);
    List<EventFlower> findEventFlowerByEvent_Id(int id);
    List<EventFlower> findEventFlowerByStatus(Status status);
    @Query("SELECT ef FROM EventFlower ef WHERE ef.status = 'ENABLE' AND ef.event.id <> :eventId")
    List<EventFlower> findEnableExcludingEvent(@Param("eventId") Integer eventId);
    List<EventFlower>findEventFlowersByFlowerSizeFlowerFlowerIDAndStatus(int id,Status status);
    EventFlower findEventFlowerById(int id);
}
