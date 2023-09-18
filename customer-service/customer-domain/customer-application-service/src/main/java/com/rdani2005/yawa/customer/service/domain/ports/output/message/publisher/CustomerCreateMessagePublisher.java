package com.rdani2005.yawa.customer.service.domain.ports.output.message.publisher;

import com.rdani2005.yawa.customer.service.domain.event.CustomerCreatedEvent;

public interface CustomerCreateMessagePublisher {
    void publish(CustomerCreatedEvent customerCreatedEvent);
}
