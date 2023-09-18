package com.rdani2005.yawa.customer.service.domain.event;

import com.rdani2005.yawa.customer.service.domain.entity.Customer;

import java.time.ZonedDateTime;

public class CustomerDeletedEvent extends CustomerEvent {
    public CustomerDeletedEvent(
            Customer customer,
            ZonedDateTime createdAt
    ) {
        super(customer, createdAt);
    }
}
