package org.example.repository;

import org.example.entity.Order;
import org.example.entity.Type;
import org.example.entity.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TypeRepository extends JpaRepository<Type, Integer> {
    Type findTypeByTypeIDAndStatus(int id, Status status);
    List<Type> findTypesByStatusOrderByMinConsumeAsc(Status status);
}
