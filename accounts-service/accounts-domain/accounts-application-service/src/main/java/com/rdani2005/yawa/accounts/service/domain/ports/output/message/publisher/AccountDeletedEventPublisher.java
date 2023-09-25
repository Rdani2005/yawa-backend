package com.rdani2005.yawa.accounts.service.domain.ports.output.message.publisher;

import com.rdani2005.yawa.accounts.service.domain.event.AccountsDeletedEvent;

/**
 * Interface for publishing account deletion events.
 */
public interface AccountDeletedEventPublisher {
    /**
     * Publishes an account deletion event.
     *
     * @param accountsDeletedEvent The event to be published.
     */
    void publish(AccountsDeletedEvent accountsDeletedEvent);
}
