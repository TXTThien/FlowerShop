package org.example.repository;

import org.example.entity.AccountGift;
import org.example.entity.RollBar;
import org.example.entity.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RollBarRepository extends JpaRepository<RollBar, Integer> {
    RollBar findRollBarByIdAndStatus(int id, Status status);

    List<RollBar> findRollBarByStatus(Status status);
    RollBar findRollBarById(int id);
}
