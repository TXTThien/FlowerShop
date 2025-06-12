package org.example.repository;

import org.example.entity.OtherCustom;
import org.example.entity.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OtherCustomRepository extends JpaRepository<OtherCustom, Integer> {
    OtherCustom findOtherCustomByOtherID(int id);
    List<OtherCustom> findOtherCustomsByStatus(Status status);
}
