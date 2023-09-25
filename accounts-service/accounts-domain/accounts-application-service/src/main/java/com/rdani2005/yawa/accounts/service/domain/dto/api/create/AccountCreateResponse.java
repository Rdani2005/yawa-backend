package com.rdani2005.yawa.accounts.service.domain.dto.api.create;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Represents a response for a newly created account.
 */
@Getter
@Builder
@AllArgsConstructor
public class AccountCreateResponse {
    private final UUID accountId;
    private final UUID customerId;
    private final String accountNumber;
    private final BigDecimal initialAmount;
    private final BigDecimal actualAmount;
    private final String message;
}
