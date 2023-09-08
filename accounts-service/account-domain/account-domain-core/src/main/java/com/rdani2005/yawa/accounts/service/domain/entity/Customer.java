package com.rdani2005.yawa.accounts.service.domain.entity;

import com.rdani2005.yawa.domain.entity.AggregateRoot;
import com.rdani2005.yawa.domain.valueobject.CustomerId;

public class Customer extends AggregateRoot<CustomerId> {
    public Customer() {
    }

    public Customer(CustomerId customerId) {
        super.setId(customerId);
    }

}
