package org.example.service.Impl;

import lombok.RequiredArgsConstructor;
import org.example.entity.RollBar;
import org.example.entity.enums.Status;
import org.example.repository.RollBarRepository;
import org.example.service.IRollBarService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RollBarServiceImpl implements IRollBarService {
    private final RollBarRepository rollBarRepository;
    @Override
    public RollBar findRollBarById(int id) {
        return rollBarRepository.findRollBarByIdAndStatus(id, Status.ENABLE);
    }

    @Override
    public List<RollBar> findAll() {
        return rollBarRepository.findRollBarByStatus(Status.ENABLE);
    }
}
