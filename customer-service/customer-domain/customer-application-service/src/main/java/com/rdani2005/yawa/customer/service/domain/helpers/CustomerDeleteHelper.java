package com.rdani2005.yawa.customer.service.domain.helpers;

import com.rdani2005.yawa.customer.service.domain.CustomerDomainService;
import com.rdani2005.yawa.customer.service.domain.dto.delete.CustomerDeleteCommandDto;
import com.rdani2005.yawa.customer.service.domain.entity.Customer;
import com.rdani2005.yawa.customer.service.domain.event.CustomerDeletedEvent;
import com.rdani2005.yawa.customer.service.domain.exception.CustomerNotFoundException;
import com.rdani2005.yawa.customer.service.domain.ports.output.repository.CustomerRepository;
import com.rdani2005.yawa.domain.valueobject.CustomerId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * The {@code CustomerDeleteHelper} class provides helper methods for deleting customer entities.
 * It interacts with the domain service, repository, and handles exceptions related to customer deletion.
 */
@Slf4j
@Component
public class CustomerDeleteHelper {
    private final CustomerDomainService customerDomainService;
    private final CustomerRepository customerRepository;

    /**
     * Constructs a new {@code CustomerDeleteHelper} with the specified dependencies.
     *
     * @param customerDomainService The domain service for managing customer-related operations.
     * @param customerRepository    The repository for managing customer entities.
     */
    public CustomerDeleteHelper(
            CustomerDomainService customerDomainService,
            CustomerRepository customerRepository
    ) {
        this.customerDomainService = customerDomainService;
        this.customerRepository = customerRepository;
    }

    /**
     * Deletes a customer entity by its unique identifier.
     *
     * @param customerDeleteCommandDto The DTO containing the customer's unique identifier to be deleted.
     * @return A {@link CustomerDeletedEvent} representing the event of deleting the customer entity.
     * @throws CustomerNotFoundException If a customer with the specified ID is not found in the database.
     */
    @Transactional
    public CustomerDeletedEvent deleteCustomer(CustomerDeleteCommandDto customerDeleteCommandDto) {
        log.info("Requested to delete a customer with id: {}", customerDeleteCommandDto.getCustomerId());
        Optional<Customer> customerResponse = customerRepository.getCustomerById(
                new CustomerId(customerDeleteCommandDto.getCustomerId())
        );
        if (customerResponse.isEmpty()) {
            log.error("Could not found customer with id: {}.", customerDeleteCommandDto.getCustomerId());
            throw new CustomerNotFoundException(
                    "Could not found customer with id: " + customerDeleteCommandDto.getCustomerId()
            );
        }

        Customer customer = customerResponse.get();
        CustomerDeletedEvent deletedEvent = customerDomainService.deleteCustomer(customer);
        customerRepository.deleteCustomer(customer);
        log.info("customer with id: {} was deleted", customerDeleteCommandDto.getCustomerId());
        return deletedEvent;
    }
}
