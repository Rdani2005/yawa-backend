package com.rdani2005.yawa.accounts.service.dataaccess.customer.adapter;

import com.rdani2005.yawa.accounts.service.dataaccess.customer.entity.CustomerEntity;
import com.rdani2005.yawa.accounts.service.dataaccess.customer.mapper.AccountsCustomerDataAccessDataMapper;
import com.rdani2005.yawa.accounts.service.dataaccess.customer.repository.CustomerJpaRepository;
import com.rdani2005.yawa.accounts.service.domain.entity.Customer;
import com.rdani2005.yawa.accounts.service.domain.ports.output.repository.CustomerRepository;
import com.rdani2005.yawa.domain.valueobject.CustomerId;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Implementation of CustomerRepository that uses Spring Data JPA.
 */
@Component
public class CustomerRepositoryImpl implements CustomerRepository {

    private final CustomerJpaRepository customerJpaRepository;
    private final AccountsCustomerDataAccessDataMapper accountsCustomerDataAccessDataMapper;

    /**
     * Constructs a CustomerRepositoryImpl with the required dependencies.
     *
     * @param customerJpaRepository                The Spring Data JPA repository for customer entities.
     * @param accountsCustomerDataAccessDataMapper The mapper to convert between customer entities and domain objects.
     */
    public CustomerRepositoryImpl(
            CustomerJpaRepository customerJpaRepository,
            AccountsCustomerDataAccessDataMapper accountsCustomerDataAccessDataMapper
    ) {
        this.customerJpaRepository = customerJpaRepository;
        this.accountsCustomerDataAccessDataMapper = accountsCustomerDataAccessDataMapper;
    }

    /**
     * @inheritDoc
     */
    @Override
    public Optional<Customer> getCustomerByCustomerId(CustomerId customerId) {
        CustomerEntity customerEntity = customerJpaRepository.getById(customerId.getValue());
        if (customerEntity == null) {
            return Optional.empty();
        }
        return Optional.of(
                accountsCustomerDataAccessDataMapper.customerEntityToCustomer(customerEntity)
        );
    }

    /**
     * @inheritDoc
     */
    @Override
    public Customer saveCustomer(Customer customer) {
        return accountsCustomerDataAccessDataMapper.customerEntityToCustomer(
                customerJpaRepository.save(
                        accountsCustomerDataAccessDataMapper.customerToCustomerEntity(customer)
                )
        );
    }

    /**
     * @inheritDoc
     */
    @Override
    public void deleteCustomer(Customer customer) {
        customerJpaRepository.delete(
                accountsCustomerDataAccessDataMapper.customerToCustomerEntity(customer)
        );
    }
}
