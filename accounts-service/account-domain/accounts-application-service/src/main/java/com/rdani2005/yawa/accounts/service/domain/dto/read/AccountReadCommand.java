package com.rdani2005.yawa.accounts.service.domain.dto.read;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * A command DTO to request the retrieval of account information.
 */
@AllArgsConstructor
@Getter
@Builder
public class AccountReadCommand {
    @NotNull
    private final UUID accountId;
}
