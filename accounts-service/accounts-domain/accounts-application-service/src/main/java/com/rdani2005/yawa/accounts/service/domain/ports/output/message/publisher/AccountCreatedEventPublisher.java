package com.rdani2005.yawa.accounts.service.domain.ports.output.message.publisher;

import com.rdani2005.yawa.accounts.service.domain.event.AccountsCreatedEvent;

/**
 * Interface for publishing account creation events.
 */
public interface AccountCreatedEventPublisher {
    /**
     * Publishes an account creation event.
     *
     * @param accountsCreatedEvent The event to be published.
     */
    void publish(AccountsCreatedEvent accountsCreatedEvent);
}
