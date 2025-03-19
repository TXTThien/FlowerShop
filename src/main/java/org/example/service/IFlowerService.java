package org.example.service;
import org.example.dto.ProductDTO;
import org.example.entity.Flower;
import org.example.entity.*;
import org.example.entity.enums.Status;

import java.util.List;
import java.util.concurrent.Flow;

public interface IFlowerService {
    List<Flower> findAll();
    List<Flower> findAllEnable();

    Flower updateProduct (Integer id, Flower newFlower);
    void deleteProduct (Integer id);
    Flower getProductById(Integer id);
    Flower findFlowerByIdEnable (Integer id);
    List<Flower> findFlowersWithPurpose(Integer purposeid);
    List<Flower>findFlowersSimilar(Integer categoryid);

    List<Flower>findByCategory(String category, Status status);

    List<Flower> findByTitle(String title, Status status);

    List<ProductDTO> find10HotestProductEnable();

    Integer HowManyBought(int id);

    List<Flower> findByPurpose(String purpose, Status status);

    List<Flower> sortFlower(int category, int propose);

    List<ProductDTO> getFlowerDTOsByFlowerIds(List<Integer> flowerIds);
}
