package com.rdani2005.yawa.accounts.service.domain.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration properties for the Accounts service.
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "accounts-service")
public class AccountsServiceConfigData {
    /**
     * Name of the Kafka topic for customer created events.
     */
    private String customerCreatedTopicName;

    /**
     * Name of the Kafka topic for customer deleted events.
     */
    private String customerDeletedTopicName;

    /**
     * Name of the Kafka topic for account created events.
     */
    private String accountCreatedTopicName;

    /**
     * Name of the Kafka topic for account deleted events.
     */
    private String accountDeletedTopicName;
}
