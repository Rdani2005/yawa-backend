package com.rdani2005.yawa.accounts.service.domain.dto.api.delete;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Represents a command to delete an account.
 */
@Getter
@Builder
@AllArgsConstructor
public class AccountDeleteCommand {
    @NotNull
    private final UUID accountId;
}
