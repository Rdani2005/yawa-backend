package com.rdani2005.yawa.accounts.service.domain.mapper;

import com.rdani2005.yawa.domain.valueobject.CustomerId;
import com.rdani2005.yawa.domain.valueobject.Money;
import org.springframework.stereotype.Component;

import com.rdani2005.yawa.accounts.service.domain.entity.Account;
import com.rdani2005.yawa.accounts.service.domain.dto.read.AccountReadResponse;
import com.rdani2005.yawa.accounts.service.domain.dto.create.*;
/**
 * Mapper class responsible for converting data transfer objects (DTOs) to domain entities and vice versa related to accounts.
 */
@Component
public final class AccountServiceDataMapper {
    /**
     * Converts an account creation DTO to an account domain entity.
     *
     * @param accountCreateCommand The DTO representing the account creation request.
     * @return An account domain entity.
     */
    public Account createDtoToAccount(AccountCreateCommand accountCreateCommand) {
        return Account
                .builder()
                .customerId(new CustomerId(accountCreateCommand.getCustomerId()))
                .initialAmount(new Money(accountCreateCommand.getInitialAmount()))
                .currentAmount(new Money(accountCreateCommand.getInitialAmount()))
                .acocuntNumber(accountCreateCommand.getAccountNumber())
                .build();
    }

    /**
     * Converts an account domain entity to an account creation response DTO.
     *
     * @param account The account domain entity.
     * @param message A message associated with the creation response.
     * @return An account creation response DTO.
     */
    public AccountCreateResponse accountToAccountCreateResponse(
            Account account,
            String message
    ) {
        return AccountCreateResponse
                .builder()
                .accountId(account.getId().getValue())
                .message(message)
                .build();
    }
    /**
     * Converts an account domain entity to an account read response DTO.
     *
     * @param account The account domain entity.
     * @return An account read response DTO.
     */
    public AccountReadResponse accountToAccountReadResponse(
            Account account
    ) {
        return AccountReadResponse
                .builder()
                .customerId(account.getCustomerId().getValue())
                .accountId(account.getId().getValue())
                .accountNumber(account.getAcocuntNumber())
                .createdAt(account.getCreatedAt())
                .initialAmount(account.getInitialAmount().getAmount())
                .currentAmount(account.getCurrentAmount().getAmount())
                .build();
    }
}
