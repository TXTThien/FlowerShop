package org.example.service;

import org.example.entity.Detect;

import java.util.List;

public interface IDetectService {
    Detect findDetectInfo(String resultFromPython);

    Detect findDetectByDetectID(int id);

    Detect findDetectByName(String flower);

    List<Detect> findAllEnable();
}
