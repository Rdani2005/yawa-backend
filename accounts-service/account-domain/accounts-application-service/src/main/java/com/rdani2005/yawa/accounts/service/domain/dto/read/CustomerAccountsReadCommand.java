package com.rdani2005.yawa.accounts.service.domain.dto.read;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * A command DTO to request the retrieval of accounts associated with a customer.
 */
@AllArgsConstructor
@Getter
@Builder
public class CustomerAccountsReadCommand {
    @NotNull
    private final UUID customerId;
}
