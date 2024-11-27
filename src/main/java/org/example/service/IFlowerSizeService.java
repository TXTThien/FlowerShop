package org.example.service;

import org.example.entity.FlowerSize;

import javax.swing.text.html.Option;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
public interface IFlowerSizeService {
    FlowerSize updateProductSize(int id, FlowerSize productSize);

    void deleteProductSize(int id);

    List<FlowerSize> findProductSizeByProductID(int id);
    FlowerSize findProductSizeByProductIDAndSize(Integer id, String size);


    void updateStock(int pz, int number);

    FlowerSize findProductSizeByID(int productSizeID);

    FlowerSize findCheapestPriceByFlowerID(Integer flowerID);
}
