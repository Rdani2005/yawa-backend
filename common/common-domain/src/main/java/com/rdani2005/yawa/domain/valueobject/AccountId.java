package com.rdani2005.yawa.domain.valueobject;

import java.util.UUID;

public class AccountId extends BaseId<UUID> {
    public AccountId(UUID value) {
        super(value);
    }
}
