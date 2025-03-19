package org.example.service.Impl;

import lombok.RequiredArgsConstructor;
import org.example.entity.EventFlower;
import org.example.entity.enums.Status;
import org.example.repository.EventFlowerRepository;
import org.example.service.IEventFlowerService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventFlowerServiceImpl implements IEventFlowerService {
    private final EventFlowerRepository eventFlowerRepository;
    @Override
    public EventFlower findEventFlowerByFlowerSizeID(Integer flowerSizeID) {
        return eventFlowerRepository.findEventFlowerByFlowerSize_FlowerSizeIDAndStatus(flowerSizeID, Status.ENABLE);
    }
}
