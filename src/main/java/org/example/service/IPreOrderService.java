package org.example.service;

import org.example.entity.FlowerSize;
import org.example.entity.Preorder;

import java.util.List;

public interface IPreOrderService {
    List<Preorder> findPreorderByAccount(int Account);

    Preorder findPreorderByPreorderID(int id);

    Preorder findPreorderByID(int id); //for admin and staff;

    List<Preorder> findPreorderWatingOrdering();
}
