package org.example.service.Impl;

import lombok.RequiredArgsConstructor;
import org.example.entity.Event;
import org.example.entity.EventFlower;
import org.example.entity.enums.Status;
import org.example.repository.EventFlowerRepository;
import org.example.service.IEventFlowerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventFlowerServiceImpl implements IEventFlowerService {
    private final EventFlowerRepository eventFlowerRepository;
    @Override
    public EventFlower findEventFlowerByFlowerSizeID(Integer flowerSizeID) {
        return eventFlowerRepository.findEventFlowerByFlowerSize_FlowerSizeIDAndStatus(flowerSizeID, Status.ENABLE);
    }

    @Override
    public List<EventFlower> findEventFlowerByEventID(Integer id) {
        return eventFlowerRepository.findEventFlowerByEvent_IdAndStatus(id, Status.ENABLE);
    }

    @Override
    public List<EventFlower> findEventFlowerByEventIDForStaff(Integer id) {
        return eventFlowerRepository.findEventFlowerByEvent_Id(id);
    }

    @Override
    public List<EventFlower> findEventFlowersByFlowerID(Integer id) {
        return eventFlowerRepository.findEventFlowersByFlowerSizeFlowerFlowerIDAndStatus(id,Status.ENABLE);
    }

    @Override
    public EventFlower findEventFlowerByEventFlowerID(Integer idEventFlower) {
        return eventFlowerRepository.findEventFlowerById(idEventFlower);
    }

    @Override
    public List<EventFlower> findEnable() {
        return eventFlowerRepository.findEventFlowerByStatus(Status.ENABLE);
    }

    @Override
    public List<EventFlower> findEnableExcept(int id) {
        return eventFlowerRepository.findEnableExcludingEvent(id);
    }


}
