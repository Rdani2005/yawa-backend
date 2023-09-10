package com.rdani2005.yawa.accounts.service.domain.ports.inputs.message.listener;

import com.rdani2005.yawa.accounts.service.domain.dto.messages.request.DepositRequest;

/**
 * Represents a listener for deposit request messages.
 */
public interface DepositRequestMessageListener {
    /**
     * Handles a deposit request message by making a deposit based on the provided information.
     *
     * @param depositRequest The deposit request message containing details of the deposit.
     */
    void makeDeposit(DepositRequest depositRequest);
}
