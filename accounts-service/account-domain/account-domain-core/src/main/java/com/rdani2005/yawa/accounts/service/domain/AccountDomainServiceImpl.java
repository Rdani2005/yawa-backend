package com.rdani2005.yawa.accounts.service.domain;

import com.rdani2005.yawa.accounts.service.domain.entity.Account;
import com.rdani2005.yawa.accounts.service.domain.event.*;
import com.rdani2005.yawa.accounts.service.domain.exception.AccountDomainException;
import com.rdani2005.yawa.domain.valueobject.Money;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static com.rdani2005.yawa.accounts.service.domain.entity.Account.UTC;

/**
 * Implementation of the {@link AccountDomainService} interface that provides
 * methods for managing accounts and handling associated events.
 */
public class AccountDomainServiceImpl implements AccountDomainService {

    /**
     * Initializes an account and triggers an event for account creation.
     *
     * @param account The account to be initialized.
     * @param initialAmount the amount that the account will have.
     * @return An event indicating the creation of the account.
     */
    @Override
    public AccountCreatedEvent initializeAccount(
            Account account,
            Money initialAmount
    ) {
        account.initializeAccount(initialAmount);
        return new AccountCreatedEvent(
                account,
                ZonedDateTime.now(ZoneId.of(UTC))
        );
    }

    /**
     * Deletes an account and triggers an event for account deletion.
     *
     * @param account The account to be deleted.
     * @return An event indicating the deletion of the account.
     */
    @Override
    public AccountDeletedEvent deleteAccount(
            Account account
    ) {
        account.validateDeleteAccount();
        return new AccountDeletedEvent(
                account,
                ZonedDateTime.now(ZoneId.of(UTC))
        );
    }

    /**
     * Approves a movement (transaction) on an account and triggers an appropriate event.
     *
     * @param account The account on which the movement is approved.
     * @param money   The amount of money associated with the movement.
     * @return An event related to the approval of the movement.
     */
    @Override
    public AccountEvent approveMovement(
            Account account,
            Money money
    ) {
        try {
            account.makeTransaction(money);
            return new AccountMovementApproved(
                    account,
                    ZonedDateTime.now(ZoneId.of(UTC))
            );
        } catch (AccountDomainException accountDomainException) {
            return new AccountMovementDeniedEvent(
                    account,
                    ZonedDateTime.now(ZoneId.of(UTC))
            );
        }
    }

    /**
     * Creates a deposit to an account and triggers an appropriate event.
     *
     * @param account The account to which the deposit is made.
     * @param money   The amount of money to be deposited.
     */
    @Override
    public AccountDepositEvent createDepositToAnAccount(
            Account account,
            Money money
    ) {
        account.makeDeposit(money);
        return new AccountDepositEvent(
                account,
                ZonedDateTime.now(ZoneId.of(UTC))
        );
    }
}
