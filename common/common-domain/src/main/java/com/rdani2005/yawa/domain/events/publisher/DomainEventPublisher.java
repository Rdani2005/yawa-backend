package com.rdani2005.yawa.domain.events.publisher;

import com.rdani2005.yawa.domain.events.DomainEvent;

public interface DomainEventPublisher<T extends DomainEvent> {
    void publish(T domainEvent);
}
