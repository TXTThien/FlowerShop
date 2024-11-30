package org.example.service;

import jakarta.validation.Valid;
import org.example.auth.RegisterRequest;
import org.example.auth.RegisterRequestForAdmin;
import org.example.entity.*;
import org.example.entity.enums.Role;

import java.util.List;
import java.util.Optional;

public interface IAccountService {

    Account getAccountById(Integer accountId);

    Account makeAccount(RegisterRequest request);


    Optional<Account> findAccountByUsername(String username);

    <S extends Account> S save(S entity);

    Account updateAccount(Integer accountId,  @Valid RegisterRequestForAdmin newAccountRequest);
    void deleteAccount (Integer id);

    void updateAccountInfo(Account account);

    void harddeleteAccount(int id);

    List<Account> getAccountByRole(Role role);
    List<Object[]> getUserAccountByType ();
}