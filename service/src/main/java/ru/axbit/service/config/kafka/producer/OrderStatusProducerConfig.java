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
import ru.axbit.service.config.kafka.AbstractKafkaConfig;

/**
 * Конфигурация продюсера.
 */
@Setter
@Getter
@EnableKafka
@Profile("orderSchedulerService")
@ConfigurationProperties(prefix = "order-status.kafka")
public class OrderStatusProducerConfig extends AbstractKafkaConfig {

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
