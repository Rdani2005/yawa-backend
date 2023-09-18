package com.rdani2005.yawa.kafka.producer;

import com.rdani2005.yawa.kafka.config.data.KafkaConfigData;
import com.rdani2005.yawa.kafka.config.data.KafkaProducerConfigData;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * The {@code KafkaProducerConfig} class provides configuration for a Kafka producer and creates the necessary
 * Spring beans to set up Kafka producer functionality.
 *
 * @param <K> The type of the Kafka message key.
 * @param <V> The type of the Kafka message value, which should extend SpecificRecordBase.
 */
@Configuration
public class KafkaProducerConfig<K extends Serializable, V extends SpecificRecordBase> {

    private final KafkaConfigData kafkaConfigData;
    private final KafkaProducerConfigData kafkaProducerConfigData;

    /**
     * Constructs a new instance of {@code KafkaProducerConfig} with the specified Kafka configuration data
     * and Kafka producer configuration data.
     *
     * @param kafkaConfigData        The Kafka configuration data containing properties like bootstrap servers
     *                               and schema registry URL.
     * @param kafkaProducerConfigData The Kafka producer configuration data containing properties like key serializer,
     *                               value serializer, and batching settings.
     */
    public KafkaProducerConfig(
            KafkaConfigData kafkaConfigData,
            KafkaProducerConfigData kafkaProducerConfigData
    ) {
        this.kafkaConfigData = kafkaConfigData;
        this.kafkaProducerConfigData = kafkaProducerConfigData;
    }

    /**
     * Create a map of producer configuration properties based on the provided Kafka configuration data
     * and Kafka producer configuration data.
     *
     * @return A map of producer configuration properties.
     */
    @Bean
    public Map<String, Object> producerConfig() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfigData.getBootstrapServers());
        props.put(kafkaConfigData.getSchemaRegistryUrlKey(), kafkaConfigData.getSchemaRegistryUrl());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, kafkaProducerConfigData.getKeySerializerClass());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, kafkaProducerConfigData.getValueSerializerClass());
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, kafkaProducerConfigData.getBatchSize() *
                kafkaProducerConfigData.getBatchSizeBoostFactor());
        props.put(ProducerConfig.LINGER_MS_CONFIG, kafkaProducerConfigData.getLingerMs());
        props.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, kafkaProducerConfigData.getCompressionType());
        props.put(ProducerConfig.ACKS_CONFIG, kafkaProducerConfigData.getAcks());
        props.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, kafkaProducerConfigData.getRequestTimeoutMs());
        props.put(ProducerConfig.RETRIES_CONFIG, kafkaProducerConfigData.getRetryCount());
        return props;
    }

    /**
     * Create a producer factory based on the producer configuration properties.
     *
     * @return A ProducerFactory for creating Kafka producers.
     */
    @Bean
    public ProducerFactory<K, V> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    /**
     * Create a KafkaTemplate for sending messages to Kafka topics using the producer factory.
     *
     * @return A KafkaTemplate for sending Kafka messages.
     */
    @Bean
    public KafkaTemplate<K, V> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
