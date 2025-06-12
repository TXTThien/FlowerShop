package org.example.service.Impl;

import lombok.RequiredArgsConstructor;
import org.example.entity.FlowerCustom;
import org.example.repository.FlowerCustomRepository;
import org.example.service.IFlowerCustomService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FlowerCustomServiceImpl implements IFlowerCustomService {
    private final FlowerCustomRepository flowerCustomRepository;
    @Override
    public FlowerCustom findFlowerByID(int id) {
        return flowerCustomRepository.findFlowerCustomByFlowerID(id);
    }
}
