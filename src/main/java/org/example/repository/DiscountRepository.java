package org.example.repository;

import org.example.entity.Account;
import org.example.entity.Discount;
import org.example.entity.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Integer> {
    List<Discount> findDiscountByStatus(Status status);

    List<Discount> findDiscountsByStatusAndAccountIsNull(Status status);
    Discount findDiscountByDiscountcodeAndStatus(String code, Status status);
    Discount findDiscountByDiscountIDAndStatus(int id, Status status);
}
