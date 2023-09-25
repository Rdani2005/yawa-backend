package com.rdani2005.yawa.accounts.service.domain.dto.api.read;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

/**
 * Represents a response containing multiple accounts associated with a customer.
 */
@Getter
@Builder
@AllArgsConstructor
public class MultipleAccountReadResponse {
    private final UUID customerId;
    private final String identification;
    private final String name;
    private final String lastName;
    private final List<AccountReadResponse> accounts;
}
