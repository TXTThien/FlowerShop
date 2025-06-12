package org.example.service.Impl;

import lombok.RequiredArgsConstructor;
import org.example.entity.OtherCustom;
import org.example.repository.OtherCustomRepository;
import org.example.service.IOtherCustomService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OtherCustomServiceImpl implements IOtherCustomService {
    private final OtherCustomRepository otherCustomRepository;
    @Override
    public OtherCustom findOtherByID(int id) {
        return otherCustomRepository.findOtherCustomByOtherID(id);
    }
}
