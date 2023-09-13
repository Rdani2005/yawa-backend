package com.rdani2005.yawa.accounts.service.domain.ports.outputs.repository;

import com.rdani2005.yawa.accounts.service.domain.entity.Customer;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {
    Optional<Customer> findCustomer(UUID customerId);
    Customer save(Customer customer);
}
