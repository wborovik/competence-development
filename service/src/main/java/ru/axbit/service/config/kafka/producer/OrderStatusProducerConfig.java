package ru.axbit.service.config.kafka.producer;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import ru.axbit.service.config.kafka.AbstractKafkaConfig;

/**
 * Конфигурация продюсера.
 */
@Setter
@Getter
@Profile("businessRulesScheduler")
public class OrderStatusProducerConfig extends AbstractKafkaConfig {

    private String topic;

    @Bean
    public ProducerFactory<String, String> kafkaFactory() {
        return new DefaultKafkaProducerFactory<>(getConfigMap());
    }

    @Bean
    public KafkaTemplate<String, String> template() {
        return new KafkaTemplate<>(kafkaFactory());
    }
}
