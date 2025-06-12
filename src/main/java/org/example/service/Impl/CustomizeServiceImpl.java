package org.example.service.Impl;

import lombok.RequiredArgsConstructor;
import org.example.entity.Customize;
import org.example.entity.enums.Status;
import org.example.repository.CustomizeRepository;
import org.example.service.ICustomizeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomizeServiceImpl implements ICustomizeService {
    private final CustomizeRepository customizeRepository;
    @Override
    public Customize findCustomizeByID(int customizeid) {
        return customizeRepository.findCustomizeByCustomID(customizeid);
    }

    @Override
    public List<Customize> findMyCustomize(int accountid) {
        return customizeRepository.findCustomizesByAccountID_AccountIDAndStatus(accountid, Status.ENABLE);
    }
}
