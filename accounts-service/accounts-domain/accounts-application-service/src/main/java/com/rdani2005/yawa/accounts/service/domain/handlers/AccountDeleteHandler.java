package com.rdani2005.yawa.accounts.service.domain.handlers;

import com.rdani2005.yawa.accounts.service.domain.dto.api.delete.AccountDeleteCommand;
import com.rdani2005.yawa.accounts.service.domain.dto.api.delete.AccountDeleteResponse;
import com.rdani2005.yawa.accounts.service.domain.event.AccountsDeletedEvent;
import com.rdani2005.yawa.accounts.service.domain.helpers.AccountDeleteHelper;
import com.rdani2005.yawa.accounts.service.domain.ports.output.message.publisher.AccountDeletedEventPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Handler for deleting user accounts.
 */
@Slf4j
@Component
public class AccountDeleteHandler {
    private final AccountDeleteHelper deleteHelper;
    private final AccountDeletedEventPublisher deletedEventPublisher;

    /**
     * Constructs an AccountDeleteHandler with the necessary dependencies.
     *
     * @param deleteHelper           The helper class for deleting accounts.
     * @param deletedEventPublisher  The publisher for the deleted account event.
     */
    public AccountDeleteHandler(
            AccountDeleteHelper deleteHelper,
            AccountDeletedEventPublisher deletedEventPublisher
    ) {
        this.deleteHelper = deleteHelper;
        this.deletedEventPublisher = deletedEventPublisher;
    }

    /**
     * Deletes an account based on the provided account delete command.
     *
     * @param accountDeleteCommand The command containing account deletion details.
     * @return The account delete response.
     */
    @Transactional
    public AccountDeleteResponse deleteAccount(AccountDeleteCommand accountDeleteCommand) {
        log.info("Requested to delete account with id: {}", accountDeleteCommand.getAccountId());
        AccountsDeletedEvent deletedEvent = deleteHelper.deleteAccount(accountDeleteCommand);
        AccountDeleteResponse deleteResponse = AccountDeleteResponse
                .builder()
                .message("Account was successfully deleted.")
                .build();

        deletedEventPublisher.publish(deletedEvent);
        return deleteResponse;
    }
}
