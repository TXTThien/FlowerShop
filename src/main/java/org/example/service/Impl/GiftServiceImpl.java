package org.example.service.Impl;

import lombok.RequiredArgsConstructor;
import org.example.entity.Gift;
import org.example.entity.RollBar;
import org.example.entity.enums.Status;
import org.example.repository.GiftRepository;
import org.example.service.IGiftService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GiftServiceImpl implements IGiftService {
    private final GiftRepository giftRepository;
    @Override
    public List<Gift> findGiftsByRollBar(RollBar rollBar) {
        return giftRepository.findGiftsByRollbaridAndStatus(rollBar, Status.ENABLE);
    }

    @Override
    public Gift findGiftByGiftID(Integer giftid) {
        return giftRepository.findGiftByIdAndStatus(giftid,Status.ENABLE);
    }

    @Override
    public List<Gift> findGiftsByRollBarByAdmin(int id) {
        return giftRepository.findGiftsByRollbarid_Id(id);
    }
}
