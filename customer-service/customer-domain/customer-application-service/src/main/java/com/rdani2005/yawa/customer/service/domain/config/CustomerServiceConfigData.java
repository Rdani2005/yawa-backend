package com.rdani2005.yawa.customer.service.domain.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for customer service properties.
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "customer-service")
public class CustomerServiceConfigData {
    private String customerCreatedTopic;
    private String customerDeletedTopic;
}
