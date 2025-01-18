package org.example.service;

import org.example.entity.FlowerSize;
import org.example.entity.Preorder;
import org.example.entity.Preorderdetail;

import java.util.List;

public interface IPreorderdetailService {
    List<Preorderdetail> findPreorderdetailByPreorder(Preorder preorder);
    List<FlowerSize> findPreorderdetailOnce();

    Integer countQuantityPreopen(FlowerSize flowerSize);
}
