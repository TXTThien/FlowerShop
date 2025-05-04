package org.example.repository;

import org.example.entity.Account;
import org.example.entity.AccountGift;
import org.example.entity.Attendance;
import org.example.entity.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountGiftRepository extends JpaRepository<AccountGift, Integer> {
    List<AccountGift> findAccountGiftsByAccount_AccountIDAndStatus(int id, Status status);
    @Query("SELECT a FROM AccountGift a WHERE a.account.accountID = :accountId AND MONTH(a.date) = :month AND a.status =:status")
    List<AccountGift> findAccountGiftsByAccountAndMonth(@Param("accountId") int accountId, @Param("month") int month, @Param("status") Status status);

    AccountGift findAccountGiftByIdAndStatus(int id, Status status);
}
