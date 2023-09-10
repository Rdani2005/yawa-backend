package com.rdani2005.yawa.accounts.service.domain.dto.read;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * A response DTO for reading account information.
 */
@AllArgsConstructor
@Getter
@Builder
public class AccountReadResponse {
    private final UUID customerId;
    private final UUID accountId;
    private final BigDecimal initialAmount;
    private final BigDecimal currentAmount;
    private final String accountNumber;
    private final ZonedDateTime createdAt;
}
