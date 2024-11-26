package org.example.service.Impl;

import lombok.RequiredArgsConstructor;
import org.example.entity.Category;
import org.example.entity.Purpose;
import org.example.entity.enums.Status;
import org.example.repository.PurposeRepository;
import org.example.service.IPurposeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PurposeServiceImpl implements IPurposeService {
    private final PurposeRepository purposeRepository;
    @Override
    public List<Purpose> findAllEnable() {
        return purposeRepository.findAllByStatus(Status.ENABLE);
    }
}
