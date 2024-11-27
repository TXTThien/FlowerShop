package org.example.service.Impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.entity.Flower;
import org.example.entity.FlowerSize;
import org.example.entity.enums.Status;
import org.example.repository.FlowerRepository;
import org.example.repository.FlowerSizeRepository;
import org.example.service.IFlowerSizeService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FlowerSizeServiceImpl implements IFlowerSizeService {
    private final FlowerSizeRepository flowerSizeRepository;
    private final FlowerRepository flowerRepository;

    @Override
    public FlowerSize updateFlowerSize(int id, FlowerSize FlowerSize) {
        FlowerSize existingFlowerSize = flowerSizeRepository.findById(id).orElse(null);
        if (existingFlowerSize != null) {
            existingFlowerSize.setStock(FlowerSize.getStock());
            existingFlowerSize.setStatus(FlowerSize.getStatus());
            existingFlowerSize.setSizeName(FlowerSize.getSizeName());
            existingFlowerSize.setLength(FlowerSize.getLength());
            existingFlowerSize.setHigh(FlowerSize.getHigh());
            existingFlowerSize.setWidth(FlowerSize.getWidth());
            existingFlowerSize.setWeight(FlowerSize.getWeight());
            existingFlowerSize.setPrice(FlowerSize.getPrice());
            existingFlowerSize.setCost(FlowerSize.getCost());
            if (FlowerSize.getFlowerSizeID() != null && FlowerSize.getFlower().getFlowerID() != null) {
                Flower product = flowerRepository.findById(FlowerSize.getFlower().getFlowerID()).orElse(null);
                if (product != null) {
                    existingFlowerSize.setFlower(product);
                } else {
                    throw new EntityNotFoundException("Product not found with ID: " + FlowerSize.getFlower().getFlowerID());
                }
            }

        }
        return null;
    }


    @Override
    public void deleteFlowerSize(int id) {
        FlowerSize FlowerSize = flowerSizeRepository.findById(id).orElse(null);
        assert FlowerSize != null;
        FlowerSize.setStatus(Status.DISABLE);
        flowerSizeRepository.save(FlowerSize);
    }

    @Override
    public List<FlowerSize> findFlowerSizeByProductID(int id) {
        return flowerSizeRepository.findFlowerSizesByFlowerFlowerIDAndStatus(id,Status.ENABLE);
    }

    @Override
    public FlowerSize findFlowerSizeByProductIDAndSize(Integer id, String size) {
        return flowerSizeRepository.findFlowerSizeByFlowerFlowerIDAndSizeNameAndStatus(id, size, Status.ENABLE);
    }


    @Override
    public void updateStock(int pz, int number) {
        flowerSizeRepository.UpdateStock(pz,number);
    }

    @Override
    public FlowerSize findFlowerSizeByID(int FlowerSizeID) {
        return flowerSizeRepository.findFlowerSizeByFlowerSizeIDAndStatus(FlowerSizeID,Status.ENABLE);
    }

    @Override
    public FlowerSize findCheapestPriceByFlowerID(Integer flowerID) {
        Pageable pageable = PageRequest.of(0, 1);
        List<FlowerSize> result = flowerSizeRepository.findLowestPriceByFlowerID(flowerID, pageable);
        FlowerSize lowestPriceFlowerSize = result.isEmpty() ? null : result.get(0);
        return lowestPriceFlowerSize;
    }
}
