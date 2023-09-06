package ru.axbit.service.config.kafka.consumer;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Configuration;
import ru.axbit.service.config.kafka.AbstractKafkaConfig;

/**
 * Конфигурация консюмера.
 */
@Setter
@Getter
@Configuration
public class OrderStatusConsumerConfig extends AbstractKafkaConfig {

}
