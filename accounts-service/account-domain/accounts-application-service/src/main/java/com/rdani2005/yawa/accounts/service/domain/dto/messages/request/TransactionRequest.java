package com.rdani2005.yawa.accounts.service.domain.dto.messages.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

/**
 * Represents a transaction request.
 */
@Builder
@AllArgsConstructor
@Getter
public class TransactionRequest {
    private String id;
    private String sagaId;
    private String accountId;
    private String customerId;
    private Instant createdAt;
    private BigDecimal transactionAmount;
    private List<String> failureMessages;
}
