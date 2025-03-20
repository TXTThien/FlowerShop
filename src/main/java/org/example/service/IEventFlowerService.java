package org.example.service;

import org.example.entity.Event;
import org.example.entity.EventFlower;

import java.util.List;

public interface IEventFlowerService {
    EventFlower findEventFlowerByFlowerSizeID(Integer flowerSizeID);

    List<EventFlower> findEventFlowerByEventID(Integer id);
}
