package com.rdani2005.yawa.accounts.service.domain.dto.delete;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * A command DTO to request the deletion of an account.
 */
@AllArgsConstructor
@Getter
@Builder
public class AccountDeleteCommand {
    @NotNull
    private final UUID accountId;
}
