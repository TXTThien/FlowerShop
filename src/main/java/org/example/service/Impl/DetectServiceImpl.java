package org.example.service.Impl;

import lombok.RequiredArgsConstructor;
import org.example.entity.Detect;
import org.example.entity.enums.Status;
import org.example.repository.DetectRepository;
import org.example.service.ICommentTypeService;
import org.example.service.IDetectService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DetectServiceImpl implements IDetectService {
    private final DetectRepository detectRepository;

    @Override
    public Detect findDetectInfo(String resultFromPython) {
        return detectRepository.findDetectByFlowerdetectAndStatus(resultFromPython, Status.ENABLE);
    }

    @Override
    public Detect findDetectByDetectID(int id) {
        return detectRepository.findDetectById(id);
    }

    @Override
    public Detect findDetectByName(String flower) {
        PageRequest pageRequest = PageRequest.of(0, 1);
        List<Detect> results = detectRepository.findTopByVietnamname(flower, Status.ENABLE, pageRequest);
        return results.isEmpty() ? null : results.get(0);
    }
}
