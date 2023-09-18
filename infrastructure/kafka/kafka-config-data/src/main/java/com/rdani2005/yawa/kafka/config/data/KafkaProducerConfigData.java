package com.rdani2005.yawa.kafka.config.data;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * The {@code KafkaProducerConfigData} class represents the configuration data required for Kafka producer integration.
 * It is used to set up properties such as key and value serializer classes, compression type, acknowledgments (acks),
 * batch size, linger time, request timeout, and retry count.
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "kafka-producer-config")
public class KafkaProducerConfigData {
    private String keySerializerClass;
    private String valueSerializerClass;
    private String compressionType;
    private String acks;
    private Integer batchSize;
    private Integer batchSizeBoostFactor;
    private Integer lingerMs;
    private Integer requestTimeoutMs;
    private Integer retryCount;
}
