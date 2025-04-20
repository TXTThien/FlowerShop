package org.example.service;

import org.example.entity.Detect;

public interface IDetectService {
    Detect findDetectInfo(String resultFromPython);

    Detect findDetectByDetectID(int id);
}
