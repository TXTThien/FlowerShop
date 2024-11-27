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
    public FlowerSize updateProductSize(int id, FlowerSize productSize) {
        FlowerSize existingProductSize = flowerSizeRepository.findById(id).orElse(null);
        if (existingProductSize != null) {
            existingProductSize.setStock(productSize.getStock());
            existingProductSize.setStatus(productSize.getStatus());
            existingProductSize.setSizeName(productSize.getSizeName());
            existingProductSize.setLength(productSize.getLength());
            existingProductSize.setHigh(productSize.getHigh());
            existingProductSize.setWidth(productSize.getWidth());
            existingProductSize.setWeight(productSize.getWeight());
            existingProductSize.setPrice(productSize.getPrice());
            existingProductSize.setCost(productSize.getCost());
            if (productSize.getFlowerSizeID() != null && productSize.getFlower().getFlowerID() != null) {
                Flower product = flowerRepository.findById(productSize.getFlower().getFlowerID()).orElse(null);
                if (product != null) {
                    existingProductSize.setFlower(product);
                } else {
                    throw new EntityNotFoundException("Product not found with ID: " + productSize.getFlower().getFlowerID());
                }
            }

        }
        return null;
    }


    @Override
    public void deleteProductSize(int id) {
        FlowerSize productSize = flowerSizeRepository.findById(id).orElse(null);
        assert productSize != null;
        productSize.setStatus(Status.DISABLE);
        flowerSizeRepository.save(productSize);
    }

    @Override
    public List<FlowerSize> findProductSizeByProductID(int id) {
        return flowerSizeRepository.findFlowerSizesByFlowerFlowerIDAndStatus(id,Status.ENABLE);
    }

    @Override
    public FlowerSize findProductSizeByProductIDAndSize(Integer id, String size) {
        return flowerSizeRepository.findFlowerSizeByFlowerFlowerIDAndSizeNameAndStatus(id, size, Status.ENABLE);
    }


    @Override
    public void updateStock(int pz, int number) {
        flowerSizeRepository.UpdateStock(pz,number);
    }

    @Override
    public FlowerSize findProductSizeByID(int productSizeID) {
        return flowerSizeRepository.findFlowerSizeByFlowerSizeIDAndStatus(productSizeID,Status.ENABLE);
    }

    @Override
    public FlowerSize findCheapestPriceByFlowerID(Integer flowerID) {
        Pageable pageable = PageRequest.of(0, 1);
        List<FlowerSize> result = flowerSizeRepository.findLowestPriceByFlowerID(flowerID, pageable);
        FlowerSize lowestPriceFlowerSize = result.isEmpty() ? null : result.get(0);
        return lowestPriceFlowerSize;
    }
}
