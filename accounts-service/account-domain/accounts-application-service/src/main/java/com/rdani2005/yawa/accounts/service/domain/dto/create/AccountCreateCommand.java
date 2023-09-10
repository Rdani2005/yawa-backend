package com.rdani2005.yawa.accounts.service.domain.dto.create;

import lombok.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * A command to create a new account.
 */
@Builder
@Getter
@AllArgsConstructor
public class AccountCreateCommand {
    @NotNull
    private final UUID customerId;
    @NotNull
    private final BigDecimal initialAmount;
    @NotNull
    private final String accountNumber;
}
