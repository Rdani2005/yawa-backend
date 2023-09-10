package com.rdani2005.yawa.accounts.service.domain;

import com.rdani2005.yawa.accounts.service.domain.entity.Account;
import com.rdani2005.yawa.accounts.service.domain.event.AccountCreatedEvent;
import com.rdani2005.yawa.accounts.service.domain.event.AccountDeletedEvent;
import com.rdani2005.yawa.accounts.service.domain.event.AccountDepositEvent;
import com.rdani2005.yawa.accounts.service.domain.event.AccountEvent;
import com.rdani2005.yawa.domain.valueobject.Money;

import java.util.List;

/**
 * Defines the contract for domain services related to account operations.
 */
public interface AccountDomainService {
    /**
     * Initializes an account and triggers an event for account creation.
     *
     * @param account The account to be initialized.
     * @param initialAmount the amount that the account will have.
     * @return An event indicating the creation of the account.
     */
    AccountCreatedEvent initializeAccount(Account account, Money initialAmount);
    /**
     * Deletes an account and triggers an event for account deletion.
     *
     * @param account The account to be deleted.
     * @return An event indicating the deletion of the account.
     */
    AccountDeletedEvent deleteAccount(Account account);
    /**
     * Approves a movement (transaction) on an account and triggers an appropriate event.
     *
     * @param account           The account on which the movement is approved.
     * @param money             The amount of money associated with the movement.
     * @return An event related to the approval of the movement.
     */
    AccountEvent approveMovement(Account account, Money money);
    /**
     * Creates a deposit to an account and triggers an appropriate event.
     *
     * @param account The account to which the deposit is made.
     * @param money   The amount of money to be deposited.
     */
    AccountDepositEvent createDepositToAnAccount(Account account, Money money);
}
