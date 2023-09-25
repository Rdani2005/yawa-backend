package com.rdani2005.yawa.accounts.service.domain.ports.output.repository;

import com.rdani2005.yawa.accounts.service.domain.entity.Customer;
import com.rdani2005.yawa.domain.valueobject.CustomerId;

import java.util.Optional;

public interface CustomerRepository {
    Optional<Customer> getCustomerByCustomerId(CustomerId customerId);
    Customer saveCustomer(Customer customer);
    void deleteCustomer(Customer customer);

}
