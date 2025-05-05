package org.example.repository;

import org.example.entity.AccountGift;
import org.example.entity.Gift;
import org.example.entity.RollBar;
import org.example.entity.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GiftRepository extends JpaRepository<Gift, Integer> {
    List<Gift> findGiftsByRollbaridAndStatus(RollBar rollBar, Status status);
    Gift findGiftByIdAndStatus(int id, Status status);

    List<Gift> findGiftsByRollbarid_Id(int id);

    Gift findGiftById(Integer giftid);
}
