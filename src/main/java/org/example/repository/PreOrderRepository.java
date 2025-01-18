package org.example.repository;

import org.example.entity.FlowerSize;
import org.example.entity.Order;
import org.example.entity.Preorder;
import org.example.entity.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PreOrderRepository extends JpaRepository<Preorder, Integer> {
    List<Preorder> findPreordersByAccount_AccountIDAndStatusOrderByDateDesc(int id, Status status);
    Preorder findPreorderByIdAndStatus(int id, Status status);
}
