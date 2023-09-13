package com.rdani2005.yawa.accounts.service.domain.outbox.scheduler.deposit;

import com.rdani2005.yawa.accounts.service.domain.outbox.model.deposit.DepositOutboxMessage;
import com.rdani2005.yawa.outbox.OutboxScheduler;
import com.rdani2005.yawa.outbox.OutboxStatus;
import com.rdani2005.yawa.saga.SagaStatus;

import lombok.extern.slf4j.Slf4j;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Component responsible for cleaning up completed deposit outbox messages.
 */
@Slf4j
@Component
public class DepositOutboxCleaner implements OutboxScheduler {
    private final DepositOutboxHelper depositOutboxHelper;

    /**
     * Constructs a new instance of DepositOutboxCleaner.
     *
     * @param depositOutboxHelper The helper for managing deposit outbox messages.
     */
    public DepositOutboxCleaner(
            DepositOutboxHelper depositOutboxHelper
    ) {
        this.depositOutboxHelper = depositOutboxHelper;
    }

    /**
     * Process and deletes the outbox messages.
     * This method is responsible for delete all completed messages at midnight
     */
    @Override
    @Scheduled(cron = "@midnight")
    public void processOutboxMessage() {
        Optional<List<DepositOutboxMessage>> outboxMessagesResponse = depositOutboxHelper
                .getDepositOutboxMessageByOutboxStatusAndSagaStatus(
                        OutboxStatus.COMPLETED,
                        SagaStatus.SUCCEEDED,
                        SagaStatus.FAILED,
                        SagaStatus.COMPENSATED
        );

        if (outboxMessagesResponse.isPresent()) {
            List<DepositOutboxMessage> outboxMessages = outboxMessagesResponse.get();
            deleteMessages(outboxMessages);
        }
    }

    private void deleteMessages(List<DepositOutboxMessage> outboxMessages) {
        log.info(
                "Received {} DepositOutboxMessage for clean-up. Payloads: {}",
                outboxMessages.size(),
                outboxMessages
                        .stream()
                        .map(DepositOutboxMessage::getPayload)
                        .collect(Collectors.joining("\n"))
        );

        depositOutboxHelper.deleteDepositOutboxMessageByOutboxStatusAndSagaStatus(
                OutboxStatus.COMPLETED,
                SagaStatus.SUCCEEDED,
                SagaStatus.FAILED,
                SagaStatus.COMPENSATED
        );

        log.info("{} DepositOutboxMessage deleted!", outboxMessages.size());
    }
}
