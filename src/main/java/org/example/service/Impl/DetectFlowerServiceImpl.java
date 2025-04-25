package org.example.service.Impl;

import lombok.RequiredArgsConstructor;
import org.example.entity.Detect;
import org.example.entity.DetectFlower;
import org.example.repository.DetectFlowerRepository;
import org.example.repository.DetectRepository;
import org.example.service.ICommentTypeService;
import org.example.service.IDetectFlowerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DetectFlowerServiceImpl implements IDetectFlowerService {
    private final DetectFlowerRepository  detectFlowerRepository;

    @Override
    public List<DetectFlower> findDetectFlowerByDetect(Detect detect) {
        return detectFlowerRepository.findDetectFlowersByDetect(detect);
    }

    @Override
    public List<DetectFlower> findDetectFlowerByDetectAndNumber(Detect detect, int n) {
        List<DetectFlower> all = detectFlowerRepository.findDetectFlowersByDetect(detect);
        return all.stream().limit(n).collect(Collectors.toList());
    }
}
