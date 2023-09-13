package com.rdani2005.yawa.accounts.service.domain.outbox.model.deposit;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

/**
 * Represents a payload for deposit approval containing relevant information about a deposit transaction.
 * This class encapsulates data such as account and restaurant identifiers, deposit amount, creation timestamp,
 * and deposit status.
 */
@Getter
@Builder
@AllArgsConstructor
public class DepositPayload {
    @JsonProperty
    private String accountId; // The identifier of the associated account
    @JsonProperty
    private String restaurantId; // The identifier of the associated restaurant
    @JsonProperty
    private BigDecimal depositAmount; // The amount of the deposit
    private ZonedDateTime createdAt; // The timestamp when the deposit was created
    @JsonProperty
    private String depositStatus; // The status of the deposit
}
