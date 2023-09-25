package com.rdani2005.yawa.accounts.service.domain.ports.output.repository;

import com.rdani2005.yawa.accounts.service.domain.entity.Account;
import com.rdani2005.yawa.domain.valueobject.AccountId;
import com.rdani2005.yawa.domain.valueobject.CustomerId;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for account-related operations.
 */
public interface AccountsRepository {
    /**
     * Saves an account.
     *
     * @param account The account to be saved.
     * @return The saved account.
     */
    Account saveAccount(Account account);
    /**
     * Reads an account by its account ID.
     *
     * @param accountId The account ID to search for.
     * @return An optional containing the found account, or empty if not found.
     */
    Optional<Account> readAccountByAccountId(AccountId accountId);
    /**
     * Reads accounts by customer ID.
     *
     * @param customerId The customer ID to filter accounts.
     * @return An optional list of accounts belonging to the specified customer, or empty if none are found.
     */
    Optional<List<Account>> readAccountsByCustomerId(CustomerId customerId);
    /**
     * Deletes an account.
     *
     * @param account The account to be deleted.
     */
    void deleteAccount(Account account);
    /**
     * Reads an account by its account number.
     *
     * @param accountNumber The account number to search for.
     * @return An optional containing the found account, or empty if not found.
     */
    Optional<Account> readAccountByAccountNumber(String accountNumber);
}
