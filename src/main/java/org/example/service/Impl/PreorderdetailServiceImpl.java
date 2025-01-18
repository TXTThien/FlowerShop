package org.example.service.Impl;

import lombok.RequiredArgsConstructor;
import org.example.entity.FlowerSize;
import org.example.entity.Preorder;
import org.example.entity.Preorderdetail;
import org.example.entity.enums.Status;
import org.example.repository.PreorderdetailRepository;
import org.example.service.IPreOrderService;
import org.example.service.IPreorderdetailService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PreorderdetailServiceImpl implements IPreorderdetailService {
    private final PreorderdetailRepository preorderdetailRepository;
    @Override
    public List<Preorderdetail> findPreorderdetailByPreorder(Preorder preorder) {
        return preorderdetailRepository.findPreorderdetailsByPreorderIDAndStatus(preorder, Status.ENABLE);
    }

    @Override
    public List<FlowerSize> findPreorderdetailOnce() {
        return preorderdetailRepository.findDistinctByFlowerSize();
    }

    @Override
    public Integer countQuantityPreopen(FlowerSize flowerSize) {
        return preorderdetailRepository.calculateTotalQuantityByFlowerSize(flowerSize);
    }
}
