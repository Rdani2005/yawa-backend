package com.rdani2005.yawa.domain.valueobject;

import java.util.UUID;

public class TransactionId extends BaseId<UUID>  {
    public TransactionId(UUID value) {
        super(value);
    }
}
