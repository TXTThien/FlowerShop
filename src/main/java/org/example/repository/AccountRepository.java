package org.example.repository;

import jakarta.transaction.Transactional;
import org.example.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    boolean existsByUsername(String username);
    boolean existsByEmail(String username);

    Optional<? extends Account> findByUsername(String username);
    Optional<? extends Account> findByEmail(String username);
    Optional<? extends Account> findByUsernameAndPassword(String username, String password);
    Optional<Account> findAccountByEmail(String email);
    Account findAccountByAccountID(int accountid);
    Optional<Account> findAccountByUsername(String username);
    @Transactional
    @Query("SELECT a.email FROM Account a WHERE a.username = :username")
    String findEmailByUsername(@Param("username") String username);


    @Transactional
    @Modifying
    @Query("UPDATE Account a SET a.password = :password WHERE a.accountID = :accountID")
    void updatePassword(@Param("password") String password, @Param("accountID") int accountID);

}