package com.rdani2005.yawa.kafka.config.data;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * The {@code KafkaConfigData} class represents the configuration data required for Kafka integration. It is used
 * to set up properties such as Kafka bootstrap servers, schema registry URL, number of partitions, and replication factor.
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "kafka-config")
public class KafkaConfigData {
    private String bootstrapServers;
    private String schemaRegistryUrlKey;
    private String schemaRegistryUrl;
    private Integer numOfPartitions;
    private Short replicationFactor;
}
