package com.rdani2005.yawa.accounts.service.domain.outbox.scheduler.deposit;

import com.rdani2005.yawa.accounts.service.domain.outbox.model.deposit.DepositOutboxMessage;
import com.rdani2005.yawa.accounts.service.domain.ports.outputs.message.publisher.deposit.DepositResponseMessagePublisher;
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
 * The scheduler for processing deposit outbox messages.
 */
@Slf4j
@Component
public class DepositOutboxScheduler implements OutboxScheduler {
    private final DepositOutboxHelper depositOutboxHelper;
    private final DepositResponseMessagePublisher depositResponseMessagePublisher;

    /**
     * Constructor for the DepositOutboxScheduler.
     *
     * @param depositOutboxHelper          The helper class for managing deposit outbox messages.
     * @param depositResponseMessagePublisher The publisher for deposit response messages.
     */
    public DepositOutboxScheduler(
            DepositOutboxHelper depositOutboxHelper,
            DepositResponseMessagePublisher depositResponseMessagePublisher
    ) {
        this.depositOutboxHelper = depositOutboxHelper;
        this.depositResponseMessagePublisher = depositResponseMessagePublisher;
    }

    /**
     * Process and send the outbox messages.
     * This method is responsible for retrieving pending deposit outbox messages with specific statuses and
     * sending them to the message bus.
     */
    @Override
    @Scheduled(
            fixedDelayString = "${accounts-service.outbox-scheduler-fixed-rate}",
            initialDelayString = "${accounts-service.outbox-scheduler-initial-delay}"
    )
    public void processOutboxMessage() {
        Optional<List<DepositOutboxMessage>> outboxMessagesResponse =
                depositOutboxHelper.getDepositOutboxMessageByOutboxStatusAndSagaStatus(
                        OutboxStatus.STARTED,
                        SagaStatus.PROCESSING
                );
        if (outboxMessagesResponse.isPresent() && !outboxMessagesResponse.get().isEmpty()) {
            List<DepositOutboxMessage> outboxMessages = outboxMessagesResponse.get();
            publishOutboxMessages(outboxMessages);
        }
    }

    /**
     * Send the outbox messages list to the message bus.
     * @param outboxMessages    The list of messages to send to message bus.
     */
    private void publishOutboxMessages(List<DepositOutboxMessage> outboxMessages) {
        log.info(
                "Received {} DepositOutboxMessage wid ids: {}, sending to message bus!",
                outboxMessages.size(),
                outboxMessages
                        .stream()
                        .map(outboxMessage -> outboxMessage.getId().toString())
                        .collect(Collectors.joining(","))
                );
        outboxMessages.forEach(
                outboxMessage -> depositResponseMessagePublisher.publish(
                        outboxMessage,
                        this::updateOutboxStatus
                )
        );
        log.info("{} DepositOutboxMessage sent to message bus!", outboxMessages.size());
    }

    /**
     * Update the outbox status for a deposit outbox message.
     *
     * @param depositOutboxMessage The deposit outbox message to update.
     * @param outboxStatus         The new outbox status.
     */
    private void updateOutboxStatus(DepositOutboxMessage depositOutboxMessage, OutboxStatus outboxStatus) {
        depositOutboxMessage.setOutboxStatus(outboxStatus);
        depositOutboxHelper.save(depositOutboxMessage);
        log.info("DepositOutboxMessage is updated with outbox status: {}", outboxStatus.name());
    }
}
