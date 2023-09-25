package com.rdani2005.yawa.accounts.service.domain.handlers;

import com.rdani2005.yawa.accounts.service.domain.dto.api.create.AccountCreateCommand;
import com.rdani2005.yawa.accounts.service.domain.dto.api.create.AccountCreateResponse;
import com.rdani2005.yawa.accounts.service.domain.event.AccountsCreatedEvent;
import com.rdani2005.yawa.accounts.service.domain.helpers.AccountCreateHelper;
import com.rdani2005.yawa.accounts.service.domain.mapper.AccountsServiceDataMapper;
import com.rdani2005.yawa.accounts.service.domain.ports.output.message.publisher.AccountCreatedEventPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Handles the creation of user accounts.
 */
@Slf4j
@Component
public class AccountCreateHandler {
    private final AccountCreateHelper accountCreateHelper;
    private final AccountsServiceDataMapper accountsServiceDataMapper;
    private final AccountCreatedEventPublisher accountCreatedEventPublisher;


    /**
     * Constructs an AccountCreateHandler with the necessary dependencies.
     *
     * @param accountCreateHelper         The helper class for creating accounts.
     * @param accountsServiceDataMapper   The data mapper for transforming account data.
     * @param accountCreatedEventPublisher The publisher for account creation events.
     */
    public AccountCreateHandler(
            AccountCreateHelper accountCreateHelper,
            AccountsServiceDataMapper accountsServiceDataMapper,
            AccountCreatedEventPublisher accountCreatedEventPublisher
    ) {
        this.accountCreateHelper = accountCreateHelper;
        this.accountsServiceDataMapper = accountsServiceDataMapper;
        this.accountCreatedEventPublisher = accountCreatedEventPublisher;
    }

    /**
     * Creates a new account based on the provided account creation command.
     *
     * @param accountCreateCommand The command containing account creation details.
     * @return The account creation response.
     */
    @Transactional
    public AccountCreateResponse createAccount(AccountCreateCommand accountCreateCommand) {
        AccountsCreatedEvent accountsCreatedEvent = accountCreateHelper.createAccount(accountCreateCommand);
        log.info("Account was successfully created with id: {}", accountsCreatedEvent.getAccount().getId().getValue());
        AccountCreateResponse createResponse = accountsServiceDataMapper.accountToAccountCreateResponse(
                accountsCreatedEvent.getAccount(),
                "Account was successfully created."
        );
        accountCreatedEventPublisher.publish(accountsCreatedEvent);
        return createResponse;
    }
}
