package ru.axbit.service.config.kafka;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * Абстрактный класс конфигурации Kafka.
 */
@Getter
@Setter
public abstract class AbstractKafkaConfig {
    private Map<String, Object> configMap;
}
