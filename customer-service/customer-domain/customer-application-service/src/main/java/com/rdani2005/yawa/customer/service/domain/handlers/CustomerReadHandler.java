package com.rdani2005.yawa.customer.service.domain.handlers;

import com.rdani2005.yawa.customer.service.domain.dto.read.CustomerReadDto;
import com.rdani2005.yawa.customer.service.domain.dto.read.CustomerReadResponseDto;
import com.rdani2005.yawa.customer.service.domain.dto.read.MultiCustomerReadResponseDto;
import com.rdani2005.yawa.customer.service.domain.entity.Customer;
import com.rdani2005.yawa.customer.service.domain.exception.CustomerException;
import com.rdani2005.yawa.customer.service.domain.mapper.CustomerDataMapper;
import com.rdani2005.yawa.customer.service.domain.ports.output.repository.CustomerRepository;
import com.rdani2005.yawa.domain.valueobject.CustomerId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * The {@code CustomerReadHandler} class provides handler methods for reading customer entities,
 * including reading all customers and reading a customer by ID. It interacts with the repository and data mapper.
 */
@Slf4j
@Component
public class CustomerReadHandler {
    private final CustomerRepository  customerRepository;
    private final CustomerDataMapper customerDataMapper;

    /**
     * Constructs a new {@code CustomerReadHandler} with the specified dependencies.
     *
     * @param customerRepository The repository for managing customer entities.
     * @param customerDataMapper The data mapper for converting between entities and DTOs.
     */
    public CustomerReadHandler(
            CustomerRepository customerRepository,
            CustomerDataMapper customerDataMapper
    ) {
        this.customerRepository = customerRepository;
        this.customerDataMapper = customerDataMapper;
    }

    /**
     * Retrieves and returns a list of all customers stored in the database.
     *
     * @return A {@link MultiCustomerReadResponseDto} containing a list of customer DTOs.
     * @throws CustomerException If there are no customers in the database.
     */
    @Transactional(readOnly = true)
    public MultiCustomerReadResponseDto readAllCustomers() {
        log.info("Requested to read all customers");
        Optional<List<Customer>> customersResponse = customerRepository.getAllCustomers();
        if (customersResponse.isEmpty()) {
            log.error("There are not any customer on database.");
            throw new CustomerException("There are not any customer on database.");
        }

        List<Customer> customers = customersResponse.get();
        List<CustomerReadResponseDto> customerReadResponseDtos =
                customerDataMapper.customersToCustomerReadResponse(customers);
        log.info("Returning all customers from DB.");
        return MultiCustomerReadResponseDto
                .builder()
                .customers(customerReadResponseDtos)
                .build();
    }

    /**
     * Retrieves and returns a customer entity by its unique identifier.
     *
     * @param customerReadDto The DTO containing the customer's unique identifier.
     * @return A {@link CustomerReadResponseDto} representing the customer entity.
     * @throws CustomerException If a customer with the specified ID is not found in the database.
     */
    @Transactional(readOnly = true)
    public CustomerReadResponseDto readCustomerById(CustomerReadDto customerReadDto) {
        log.info("Requested to read a customer with id: {}", customerReadDto.getCustomerId());
        Optional<Customer> customerResponse = customerRepository.getCustomerById(
                new CustomerId(customerReadDto.getCustomerId())
        );
        if (customerResponse.isEmpty()) {
            log.error("Could not found customer with id: {}.", customerReadDto.getCustomerId());
            throw new CustomerException("Could not found customer with id: " + customerReadDto.getCustomerId());
        }

        Customer customer = customerResponse.get();
        log.info("Returning customer with id: {}.", customer.getId().getValue());
        return customerDataMapper.customerToCustomerReadResponse(customer);
    }
}
