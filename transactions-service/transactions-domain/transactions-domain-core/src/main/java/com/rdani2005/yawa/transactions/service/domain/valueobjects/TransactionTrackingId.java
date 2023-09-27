package com.rdani2005.yawa.transactions.service.domain.valueobjects;

import com.rdani2005.yawa.domain.valueobject.BaseId;

import java.util.UUID;

public class TransactionTrackingId extends BaseId<UUID> {
    public TransactionTrackingId(UUID value) {
        super(value);
    }
}
