package com.rdani2005.yawa.accounts.service.domain.dto.api.read;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Represents the response containing account information.
 */
@Getter
@Builder
@AllArgsConstructor
public class AccountReadResponse {
    private final UUID accountId;
    private final String accountNumber;
    private final BigDecimal initialAmount;
    private final BigDecimal actualAmount;
}
