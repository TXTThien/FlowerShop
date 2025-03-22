package org.example.service;

import org.example.entity.Event;

import java.util.List;

public interface IEventService {
    List<Event> findEventEnable();

    Event findEventByID(int id);
}
