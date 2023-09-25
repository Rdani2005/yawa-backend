package com.rdani2005.yawa.accounts.service.domain.dto.messages.customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * Represents a data model for a customer.
 */
@Getter
@Builder
@AllArgsConstructor
public class CustomerModel {
    private final UUID customerId;
    private final String name;
    private final String lastName;
    private final String identification;
    private final ZonedDateTime birthDay;
    private final ZonedDateTime createdAt;
}
