package ru.axbit.service.service.business.processing.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.axbit.service.service.business.robot.impl.StatusChangeServiceImpl;

import javax.transaction.Transactional;

/**
 * Сервис получения сообщений из kafka топика для дальнейшей обработки.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Profile("orderSchedulerService")
public class OrderStatusConsumerService {

    private final StatusChangeServiceImpl changeService;

    @Transactional
    @KafkaListener(topics = {"${order-status.kafka.topic}"},
            containerFactory = "kafkaListenerContainerFactory")
    public void consume(Long message) {
        log.info("Consumed message from OrderStatus topic : {}", message);
        changeService.execute(message);
    }
}
