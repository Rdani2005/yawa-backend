package com.rdani2005.yawa.customer.service.domain.handlers;

import com.rdani2005.yawa.customer.service.domain.dto.delete.CustomerDeleteCommandDto;
import com.rdani2005.yawa.customer.service.domain.dto.delete.CustomerDeleteResponseDto;
import com.rdani2005.yawa.customer.service.domain.event.CustomerDeletedEvent;
import com.rdani2005.yawa.customer.service.domain.helpers.CustomerDeleteHelper;
import com.rdani2005.yawa.customer.service.domain.ports.output.message.publisher.CustomerDeletedMessagePublisher;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * The {@code CustomerDeleteHandler} class provides a handler for deleting customer entities.
 * It interacts with the {@link CustomerDeleteHelper} to perform customer deletion operations and publishes
 * events for customer deletion using the specified {@link CustomerDeletedMessagePublisher}.
 */
@Slf4j
@Component
public class CustomerDeleteHandler {
    private final CustomerDeleteHelper customerDeleteHelper;
    private final CustomerDeletedMessagePublisher customerDeletedMessagePublisher;

    /**
     * Constructs a new {@code CustomerDeleteHandler} with the specified dependencies.
     *
     * @param customerDeleteHelper             The helper class for deleting customer entities.
     * @param customerDeletedMessagePublisher The publisher for customer deletion events.
     */
    public CustomerDeleteHandler(
            CustomerDeleteHelper customerDeleteHelper,
            CustomerDeletedMessagePublisher customerDeletedMessagePublisher
    ) {
        this.customerDeleteHelper = customerDeleteHelper;
        this.customerDeletedMessagePublisher = customerDeletedMessagePublisher;
    }

    /**
     * Deletes a customer entity based on the provided delete command.
     *
     * @param customerDeleteCommandDto The DTO containing the customer's unique identifier to be deleted.
     * @return A {@link CustomerDeleteResponseDto} indicating the success of the customer deletion.
     */
    @Transactional
    public CustomerDeleteResponseDto deleteCustomer(CustomerDeleteCommandDto customerDeleteCommandDto) {
        CustomerDeletedEvent deletedEvent = customerDeleteHelper.deleteCustomer(customerDeleteCommandDto);
        log.info("Customer with id: {} was deleted", deletedEvent.getCustomer().getId().getValue());
        CustomerDeleteResponseDto deleteResponseDto = CustomerDeleteResponseDto
                .builder()
                .message("Customer was deleted successfully")
                .build();
        customerDeletedMessagePublisher.publish(deletedEvent);
        return deleteResponseDto;
    }
}
