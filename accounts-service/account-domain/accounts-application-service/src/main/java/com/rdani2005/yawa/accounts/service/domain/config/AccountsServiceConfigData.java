package com.rdani2005.yawa.accounts.service.domain.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration properties for the Accounts Service.
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "accounts-service")
public class AccountsServiceConfigData {
    private String depositRequestTopicName;
    private String depositResponseTopicName;
    private String transactionRequestTopicName;
    private String transactionResponseTopicName;
    private String accountDeleteRequestTopicName;
    private String accountCreatedRequestTopicName;
}
