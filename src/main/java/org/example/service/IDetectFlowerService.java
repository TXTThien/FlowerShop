package org.example.service;

import org.example.entity.Detect;
import org.example.entity.DetectFlower;

import java.util.List;

public interface IDetectFlowerService {
    List<DetectFlower> findDetectFlowerByDetect(Detect detect);

    List<DetectFlower> findDetectFlowerByDetectAndNumber(Detect detect, int n);
}
