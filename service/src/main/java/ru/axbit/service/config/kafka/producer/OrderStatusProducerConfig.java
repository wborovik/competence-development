package ru.axbit.service.config.kafka.producer;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.Map;

/**
 * Конфигурация продюсера.
 */
@Setter
@Getter
@EnableKafka
@Profile("businessRulesScheduler")
@ConfigurationProperties(prefix = "order-status.producer.kafka")
public class OrderStatusProducerConfig {

    private Map<String, Object> configMap;
    private String topic;

    @Bean
    public ProducerFactory<String, Long> producerFactory() {
        return new DefaultKafkaProducerFactory<>(getConfigMap());
    }

    @Bean
    public KafkaTemplate<String, Long> template() {
        return new KafkaTemplate<>(producerFactory());
    }
}
