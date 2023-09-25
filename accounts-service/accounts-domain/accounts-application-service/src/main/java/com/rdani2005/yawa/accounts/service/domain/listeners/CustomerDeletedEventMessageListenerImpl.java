package com.rdani2005.yawa.accounts.service.domain.listeners;

import com.rdani2005.yawa.accounts.service.domain.dto.messages.customer.CustomerModel;
import com.rdani2005.yawa.accounts.service.domain.mapper.AccountsServiceDataMapper;
import com.rdani2005.yawa.accounts.service.domain.ports.input.listener.CustomerDeletedEventListener;
import com.rdani2005.yawa.accounts.service.domain.ports.output.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Listener for handling customer deleted events.
 */
@Slf4j
@Service
public class CustomerDeletedEventMessageListenerImpl implements CustomerDeletedEventListener {
    private final CustomerRepository customerRepository;
    private final AccountsServiceDataMapper accountsServiceDataMapper;

    /**
     * Constructs a CustomerDeletedEventMessageListenerImpl.
     *
     * @param customerRepository       The repository for customer data.
     * @param accountsServiceDataMapper The mapper for transforming data between DTOs and entities.
     */
    public CustomerDeletedEventMessageListenerImpl(
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
    public void deleteCustomer(CustomerModel customerModel) {
        customerRepository.deleteCustomer(
                accountsServiceDataMapper.customerModelToCustomer(customerModel)
        );

        log.info("Customer was successfully deleted.");
    }
}
