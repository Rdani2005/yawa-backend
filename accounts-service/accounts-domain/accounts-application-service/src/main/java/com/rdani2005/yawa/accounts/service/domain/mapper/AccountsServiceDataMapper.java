package com.rdani2005.yawa.accounts.service.domain.mapper;

import com.rdani2005.yawa.accounts.service.domain.dto.api.create.AccountCreateCommand;
import com.rdani2005.yawa.accounts.service.domain.dto.api.create.AccountCreateResponse;
import com.rdani2005.yawa.accounts.service.domain.dto.api.read.AccountReadResponse;
import com.rdani2005.yawa.accounts.service.domain.dto.messages.customer.CustomerModel;
import com.rdani2005.yawa.accounts.service.domain.entity.Account;
import com.rdani2005.yawa.accounts.service.domain.entity.Customer;
import com.rdani2005.yawa.domain.valueobject.CustomerId;
import com.rdani2005.yawa.domain.valueobject.Money;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper class for mapping data between DTOs and entities in the Accounts service.
 */
@Component
public class AccountsServiceDataMapper {
    /**
     * Maps an AccountCreateCommand to an Account entity.
     *
     * @param accountCreateCommand The AccountCreateCommand to be mapped.
     * @return The mapped Account entity.
     */
    public Account accountCreateCommandToAccount(
            AccountCreateCommand accountCreateCommand
    ) {
        return Account
                .Builder
                .builder()
                .customerId(new CustomerId(accountCreateCommand.getCustomerId()))
                .initialAmount(new Money(accountCreateCommand.getInitialAmount()))
                .accountNumber(accountCreateCommand.getAccountNumber())
                .build();
    }

    /**
     * Maps an Account entity to an AccountCreateResponse with a message.
     *
     * @param account The Account entity to be mapped.
     * @param message The message to include in the response.
     * @return The mapped AccountCreateResponse.
     */
    public AccountCreateResponse accountToAccountCreateResponse(
            Account account,
            String message
    ) {
        return AccountCreateResponse
                .builder()
                .accountId(account.getId().getValue())
                .customerId(account.getCustomerId().getValue())
                .accountNumber(account.getAccountNumber())
                .initialAmount(account.getInitialAmount().getAmount())
                .actualAmount(account.getActualAmount().getAmount())
                .message(message)
                .build();
    }

    /**
     * Maps an Account entity to an AccountReadResponse.
     *
     * @param account The Account entity to be mapped.
     * @return The mapped AccountReadResponse.
     */
    public AccountReadResponse accountToAccountReadResponse(Account account) {
        return AccountReadResponse
                .builder()
                .accountId(account.getId().getValue())
                .accountNumber(account.getAccountNumber())
                .initialAmount(account.getInitialAmount().getAmount())
                .actualAmount(account.getActualAmount().getAmount())
                .build();
    }

    /**
     * Maps a list of  Accounts entities to a list AccountReadResponses.
     *
     * @param accounts The Accounts entities to be mapped.
     * @return The mapped list AccountReadResponses.
     */
    public List<AccountReadResponse> accountsToReadResponse(List<Account> accounts) {
        return accounts
                .stream()
                .map(this::accountToAccountReadResponse)
                .collect(Collectors.toList());
    }

    /**
     * Maps a CustomerModel to a Customer entity.
     *
     * @param customerModel The CustomerModel to be mapped.
     * @return The mapped Customer entity.
     */
    public Customer customerModelToCustomer(CustomerModel customerModel) {
        return Customer
                .builder()
                .id(new CustomerId(customerModel.getCustomerId()))
                .identification(customerModel.getIdentification())
                .name(customerModel.getName())
                .lastName(customerModel.getLastName())
                .build();
    }
}
