package com.rdani2005.yawa.accounts.service.domain.ports.inputs.message.listener;

import com.rdani2005.yawa.accounts.service.domain.dto.messages.request.TransactionRequest;

/**
 * Represents a listener for transaction request messages.
 */
public interface TransactionRequestMessageListener {
    /**
     * Handles a transaction request message by making a transaction based on the provided information.
     *
     * @param transactionRequest The transaction request message containing details of the transaction.
     */
    void makeTransaction(TransactionRequest transactionRequest);
}
