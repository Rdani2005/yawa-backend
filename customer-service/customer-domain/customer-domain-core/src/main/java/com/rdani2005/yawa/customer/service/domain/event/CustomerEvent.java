package com.rdani2005.yawa.customer.service.domain.event;

import com.rdani2005.yawa.customer.service.domain.entity.Customer;
import com.rdani2005.yawa.domain.events.DomainEvent;

import java.time.ZonedDateTime;

public abstract class CustomerEvent implements DomainEvent<Customer> {
    private final Customer customer;
    private final ZonedDateTime createdAt;

    public CustomerEvent(
            Customer customer,
            ZonedDateTime createdAt
    ) {
        this.customer = customer;
        this.createdAt = createdAt;
    }

    public Customer getCustomer() {
        return customer;
    }
}
