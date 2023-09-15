package ru.axbit.service.config.kafka.consumer;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.LoggingErrorHandler;

import java.util.Map;

/**
 * Конфигурация консюмера.
 */
@Setter
@Getter
@Configuration
@Profile("orderSchedulerService")
@ConfigurationProperties(prefix = "order-status.consumer.kafka")
public class OrderStatusConsumerConfig {

    private Map<String, Object> configMap;
    private String topic;

    /**
     * Фабрика консюмера.
     *
     * @return возвращает экземпляр {@link ConsumerFactory}.
     */
    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(getConfigMap());
    }

    /**
     * Фабрика для создания контейнера.
     *
     * @param consumerFactory принимает {@link ConsumerFactory}.
     * @return возвращает {@link ConcurrentKafkaListenerContainerFactory}.
     */
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Long> kafkaListenerContainerFactory(
            ConsumerFactory<String, Long> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, Long> listenerFactory =
                new ConcurrentKafkaListenerContainerFactory<>();
        listenerFactory.setConsumerFactory(consumerFactory);
        listenerFactory.setErrorHandler(new LoggingErrorHandler());
        return listenerFactory;
    }

    /**
     * Шаблон взаимодействия с Kafka.
     *
     * @param producerFactory принимает {@link ProducerFactory}.
     * @return возвращает объект {@link KafkaTemplate}.
     */
    @Bean
    public KafkaTemplate<String, Long> kafkaTemplate(ProducerFactory<String, Long> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }
}
