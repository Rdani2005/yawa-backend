package com.rdani2005.yawa.customer.service.domain.ports.output.message.publisher;

import com.rdani2005.yawa.customer.service.domain.event.CustomerDeletedEvent;

public interface CustomerDeletedMessagePublisher {
    void publish(CustomerDeletedEvent customerDeletedEvent);
}
