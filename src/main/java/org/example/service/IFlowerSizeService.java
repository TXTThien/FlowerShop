package org.example.service;

import org.example.entity.FlowerSize;

import javax.swing.text.html.Option;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
public interface IFlowerSizeService {
    FlowerSize updateFlowerSize(int id, FlowerSize FlowerSize);

    void deleteFlowerSize(int id);

    List<FlowerSize> findFlowerSizeByProductID(int id);
    FlowerSize findFlowerSizeByProductIDAndSize(Integer id, String size);


    void updateStock(int pz, int number);

    FlowerSize findFlowerSizeByID(int FlowerSizeID);

    FlowerSize findCheapestPriceByFlowerID(Integer flowerID);
}
