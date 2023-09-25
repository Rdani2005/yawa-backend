package com.rdani2005.yawa.accounts.service.domain.dto.api.create;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * Represents a command to create a new account.
 */
@Getter
@Builder
@AllArgsConstructor
public class AccountCreateCommand {
    @NotNull
    private UUID customerId;
    @NotNull
    private String accountNumber;
    @NotNull
    private BigDecimal initialAmount;
}
