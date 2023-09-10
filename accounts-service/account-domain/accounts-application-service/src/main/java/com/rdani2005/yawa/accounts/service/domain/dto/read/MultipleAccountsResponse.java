package com.rdani2005.yawa.accounts.service.domain.dto.read;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

/**
 * A response DTO for retrieving multiple accounts associated with a customer.
 */
@AllArgsConstructor
@Getter
@Builder
public class MultipleAccountsResponse {
    private final UUID customerId;
    private final List<AccountReadResponse> accounts;
}
