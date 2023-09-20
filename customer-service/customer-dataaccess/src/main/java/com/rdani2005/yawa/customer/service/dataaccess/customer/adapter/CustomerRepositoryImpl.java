package com.rdani2005.yawa.customer.service.dataaccess.customer.adapter;

import com.rdani2005.yawa.customer.service.dataaccess.customer.entity.CustomerEntity;
import com.rdani2005.yawa.customer.service.dataaccess.customer.mapper.CustomerDataAccessMapper;
import com.rdani2005.yawa.customer.service.dataaccess.customer.repository.CustomerJpaRepository;
import com.rdani2005.yawa.customer.service.domain.entity.Customer;
import com.rdani2005.yawa.customer.service.domain.ports.output.repository.CustomerRepository;
import com.rdani2005.yawa.domain.valueobject.CustomerId;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The {@code CustomerRepositoryImpl} class is an implementation of the {@link CustomerRepository} interface
 * that interacts with the data access layer to perform CRUD operations on customer data. It uses a
 * {@link CustomerJpaRepository} and a {@link CustomerDataAccessMapper} to interact with the underlying data source.
 */
@Component
public class CustomerRepositoryImpl implements CustomerRepository {
    private final CustomerJpaRepository customerJpaRepository;
    private final CustomerDataAccessMapper customerDataAccessMapper;

    /**
     * Constructs a new {@code CustomerRepositoryImpl} with the provided dependencies.
     *
     * @param customerJpaRepository     The repository for interacting with customer data in the data access layer.
     * @param customerDataAccessMapper  The mapper for converting between domain and data access layer objects.
     */
    public CustomerRepositoryImpl(
            CustomerJpaRepository customerJpaRepository,
            CustomerDataAccessMapper customerDataAccessMapper
    ) {
        this.customerJpaRepository = customerJpaRepository;
        this.customerDataAccessMapper = customerDataAccessMapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Customer createCustomer(Customer customer) {
        return customerDataAccessMapper.customerEntityToCustomer(
                customerJpaRepository.save(
                        customerDataAccessMapper.customerToCustomerEntity(customer)
                )
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Customer> getCustomerById(CustomerId customerId) {
        return Optional.of(
                customerDataAccessMapper.customerEntityToCustomer(
                        customerJpaRepository.getById(customerId.getValue())
                )
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Customer> getCustomerByIdentification(String identification) {
        return Optional.of(
                customerDataAccessMapper.customerEntityToCustomer(
                        customerJpaRepository.getCustomerEntityByIdentification(identification)
                )
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<List<Customer>> getAllCustomers() {
        return Optional.of(
                customerJpaRepository
                        .findAll()
                        .stream()
                        .map(customerDataAccessMapper::customerEntityToCustomer)
                        .collect(Collectors.toList())
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteCustomer(Customer customer) {
        CustomerEntity customerEntity = customerDataAccessMapper.customerToCustomerEntity(customer);
        customerJpaRepository.delete(customerEntity);
    }
}
