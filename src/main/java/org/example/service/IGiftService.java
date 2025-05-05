package org.example.service;

import org.example.entity.Gift;
import org.example.entity.RollBar;

import java.util.List;

public interface IGiftService {
    List<Gift> findGiftsByRollBar(RollBar rollBar);

    Gift findGiftByGiftID(Integer giftid);

    List<Gift> findGiftsByRollBarByAdmin(int id);
}
