package com.rdani2005.yawa.accounts.service.domain.ports.outputs.repository;

import com.rdani2005.yawa.accounts.service.domain.entity.Account;
import com.rdani2005.yawa.domain.valueobject.AccountId;
import com.rdani2005.yawa.domain.valueobject.CustomerId;

import java.util.List;
import java.util.Optional;

public interface AccountRepository {
    Optional<List<Account>> getAccountsByCustomer(CustomerId customerId);
    Optional<Account> getAccountById(AccountId accountId);
    Account createAccount(Account account);
    void deleteAccount(Account account);
}
