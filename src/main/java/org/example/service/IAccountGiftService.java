package org.example.service;

import org.example.entity.AccountGift;

import java.util.List;

public interface IAccountGiftService {
    List<AccountGift> findAccountGiftByAccountID(int account);

    List<AccountGift> findAccountGiftByAccountIDAndMonth(int accountid, int month);

    AccountGift findById(int id);
}
