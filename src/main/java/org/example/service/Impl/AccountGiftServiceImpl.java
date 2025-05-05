package org.example.service.Impl;

import lombok.RequiredArgsConstructor;
import org.example.entity.AccountGift;
import org.example.entity.enums.Status;
import org.example.repository.AccountGiftRepository;
import org.example.service.IAccountGiftService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountGiftServiceImpl implements IAccountGiftService {
    private final AccountGiftRepository accountGiftRepository;
    @Override
    public List<AccountGift> findAccountGiftByAccountID(int account) {
        return accountGiftRepository.findAccountGiftsByAccount_AccountIDAndStatus(account, Status.ENABLE);
    }

    @Override
    public List<AccountGift> findAccountGiftByAccountIDAndMonth(int accountid, int month) {
        return accountGiftRepository.findAccountGiftsByAccountAndMonth(accountid,month,Status.ENABLE);
    }

    @Override
    public AccountGift findById(int id) {
        return accountGiftRepository.findAccountGiftByIdAndStatus(id, Status.ENABLE);
    }

    @Override
    public List<AccountGift> findAllByAdmin() {
        return accountGiftRepository.findAll();
    }

    @Override
    public AccountGift findByIDByAdmin(int id) {
        return accountGiftRepository.findAccountGiftById(id);
    }
}
