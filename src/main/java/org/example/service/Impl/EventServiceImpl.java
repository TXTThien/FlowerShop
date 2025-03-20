package org.example.service.Impl;

import lombok.RequiredArgsConstructor;
import org.example.entity.Event;
import org.example.entity.enums.Status;
import org.example.repository.EventRepository;
import org.example.service.IEventService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements IEventService {
    private final EventRepository eventRepository;
    @Override
    public List<Event> findEventEnable() {
        return eventRepository.findEventsByStatus(Status.ENABLE);
    }
}
