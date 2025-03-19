package org.example.service;

import org.example.entity.EventFlower;

public interface IEventFlowerService {
    EventFlower findEventFlowerByFlowerSizeID(Integer flowerSizeID);
}
