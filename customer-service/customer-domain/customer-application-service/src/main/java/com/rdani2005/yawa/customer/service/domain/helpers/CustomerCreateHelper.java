package com.rdani2005.yawa.customer.service.domain.helpers;

import com.rdani2005.yawa.customer.service.domain.CustomerDomainService;
import com.rdani2005.yawa.customer.service.domain.dto.create.CustomerCreateCommandDto;
import com.rdani2005.yawa.customer.service.domain.entity.Customer;
import com.rdani2005.yawa.customer.service.domain.event.CustomerCreatedEvent;
import com.rdani2005.yawa.customer.service.domain.exception.CustomerException;
import com.rdani2005.yawa.customer.service.domain.mapper.CustomerDataMapper;
import com.rdani2005.yawa.customer.service.domain.ports.output.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * The {@code CustomerCreateHelper} class provides helper methods for creating customer entities,
 * including validation and initialization, and interacts with domain services, mappers, and repositories.
 */
@Slf4j
@Component
public class CustomerCreateHelper {
    private final CustomerDomainService customerDomainService;
    private final CustomerRepository customerRepository;
    private final CustomerDataMapper customerDataMapper;

    /**
     * Constructs a new {@code CustomerCreateHelper} with the specified dependencies.
     *
     * @param customerDomainService The domain service responsible for customer creation.
     * @param customerRepository    The repository for managing customer entities.
     * @param customerDataMapper    The data mapper for converting between DTOs and entities.
     */
    public CustomerCreateHelper(
            CustomerDomainService customerDomainService,
            CustomerRepository customerRepository,
            CustomerDataMapper customerDataMapper
    ) {
        this.customerDomainService = customerDomainService;
        this.customerRepository = customerRepository;
        this.customerDataMapper = customerDataMapper;
    }

    /**
     * Persists a new customer based on the provided {@link CustomerCreateCommandDto}, initiates the customer
     * creation process, and returns a customer creation event. It also handles error cases, such as failing to save
     * the customer entity.
     *
     * @param customerCreateCommandDto The DTO containing customer creation information.
     * @return A {@link CustomerCreatedEvent} representing the event of creating a customer entity.
     * @throws CustomerException If an error occurs during customer creation.
     */
    @Transactional
    public CustomerCreatedEvent persistCustomer(CustomerCreateCommandDto customerCreateCommandDto) {
        checkCustomerWithIdentificationExists(customerCreateCommandDto.getIdentification());
        Customer customer = customerDataMapper
                .createCustomerCommandToCustomer(customerCreateCommandDto);
        CustomerCreatedEvent customerCreatedEvent = customerDomainService
                .validateAndInitializeCustomer(customer);
        Customer savedCustomer = customerRepository.createCustomer(customer);
        if (savedCustomer == null) {
            log.error("Could not save customer with name: {}", customer.getName());
            throw new CustomerException("Could not save customer with name " +
                    customer.getName());
        }
        log.info("Returning CustomerCreatedEvent for customer id: {}", savedCustomer.getId().getValue());
        return customerCreatedEvent;
    }

    @Transactional(readOnly = true)
    public void checkCustomerWithIdentificationExists(
            String identification
    ) {
        Optional<Customer> customer = customerRepository.getCustomerByIdentification(identification);
        if (customer.isPresent()) {
            throw  new CustomerException("There is already a customer with identification: " + identification);
        }
    }
}
