package com.rdani2005.yawa.accounts.service.domain.outbox.model.transaction;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

/**
 * Represents the payload data for a transaction, containing information about the transaction.
 * This class encapsulates data such as account identifier, restaurant identifier, transaction amount,
 * creation timestamp, and deposit status.
 */
@Getter
@Builder
@AllArgsConstructor
public class TransactionPayload {
    @JsonProperty
    private String accountId; // The identifier of the associated account
    @JsonProperty
    private String restaurantId; // The identifier of the associated restaurant
    @JsonProperty
    private BigDecimal transactionAmount; // The amount of the transaction
    private ZonedDateTime createdAt; // The timestamp when the transaction was created
    @JsonProperty
    private String depositStatus; // The status of the deposit
}
