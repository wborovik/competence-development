package ru.axbit.service.service.business.processing.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.axbit.service.config.kafka.producer.OrderStatusProducerConfig;

/**
 * Сервис отправки команд на старт обработки бизнес-правил.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Profile("orderSchedulerService")
public class OrderStatusProducerService {
    private final KafkaTemplate<String, Long> template;
    private final OrderStatusProducerConfig config;

    public void sendMessage(Long message) {
        template.send(config.getTopic(), message);
    }
}
