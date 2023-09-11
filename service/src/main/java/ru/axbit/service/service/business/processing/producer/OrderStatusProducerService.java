package ru.axbit.service.service.business.processing.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.axbit.service.config.kafka.producer.OrderStatusProducerConfig;

/**
 * Сервис продюсера кафки.
 */
@Slf4j
@Service
@Profile("orderSchedulerService")
@RequiredArgsConstructor
public class OrderStatusProducerService {
    private final KafkaTemplate<String, String> template;
    private final OrderStatusProducerConfig config;
}
