package org.example.service.Impl;

import lombok.RequiredArgsConstructor;
import org.example.entity.CustomDetail;
import org.example.repository.CustomDetailRepository;
import org.example.service.ICustomDetailService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomDetailServiceImpl implements ICustomDetailService {
    private final CustomDetailRepository customDetailRepository;
    @Override
    public List<CustomDetail> findByCustomID(int id) {
        return customDetailRepository.findCustomDetailsByCustomize_CustomID(id);
    }
}
