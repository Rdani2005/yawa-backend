package com.rdani2005.yawa.accounts.service.domain.dto.messages.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

/**
 * Represents a deposit request.
 */
@Builder
@AllArgsConstructor
@Getter
public class DepositRequest {
    private String id;
    private String sagaId;
    private String accountId;
    private String customerId;
    private Instant createdAt;
    private BigDecimal depositAmount;
    private List<String> failureMessages;
}
