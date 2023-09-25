package com.rdani2005.yawa.accounts.service.domain.listeners;

import com.rdani2005.yawa.accounts.service.domain.dto.messages.customer.CustomerModel;
import com.rdani2005.yawa.accounts.service.domain.entity.Customer;
import com.rdani2005.yawa.accounts.service.domain.exception.AccountsDomainException;
import com.rdani2005.yawa.accounts.service.domain.mapper.AccountsServiceDataMapper;
import com.rdani2005.yawa.accounts.service.domain.ports.input.listener.CustomerCreatedEventListener;
import com.rdani2005.yawa.accounts.service.domain.ports.output.repository.CustomerRepository;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

/**
 * Listener for handling customer created events.
 */
@Slf4j
@Service
public class CustomerCreatedEventMessageListenerImpl implements CustomerCreatedEventListener {

    private final CustomerRepository customerRepository;
    private final AccountsServiceDataMapper accountsServiceDataMapper;

    /**
     * Constructs a CustomerCreatedEventMessageListenerImpl.
     *
     * @param customerRepository       The repository for customer data.
     * @param accountsServiceDataMapper The mapper for transforming data between DTOs and entities.
     */
    public CustomerCreatedEventMessageListenerImpl(
            CustomerRepository customerRepository,
            AccountsServiceDataMapper accountsServiceDataMapper
    ) {
        this.customerRepository = customerRepository;
        this.accountsServiceDataMapper = accountsServiceDataMapper;
    }


    /**
     * @inheritDoc
     */
    @Override
    public void CustomerCreated(CustomerModel customerModel) {
        Customer customer = customerRepository.saveCustomer(
                accountsServiceDataMapper.customerModelToCustomer(customerModel)
        );

        if (customer == null) {
            log.error("Customer with id: {} could not be added.", customerModel.getCustomerId());
            throw new AccountsDomainException(
                    "Customer with id: " + customerModel.getCustomerId() + " could not be added."
            );
        }

        log.info("Customer with id: {} was successfully added to database.", customer.getId().getValue());
    }
}
