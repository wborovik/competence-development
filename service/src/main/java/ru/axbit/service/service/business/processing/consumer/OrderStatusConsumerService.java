package ru.axbit.service.service.business.processing.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.axbit.domain.repository.ClsOrderStatusRepository;
import ru.axbit.domain.repository.WorkOrderRepository;

import javax.transaction.Transactional;

/**
 * Сервис получения сообщений топика OrderStatus для дальнейшей обработки.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Profile("orderSchedulerService")
public class OrderStatusConsumerService {

    private static final String EXECUTED_ORDER_STATUS = "executed";
    private final ClsOrderStatusRepository orderStatusRepository;
    private final WorkOrderRepository orderRepository;

    @Transactional
    @KafkaListener(topics = {"${order-status.consumer.kafka.topic}"},
            containerFactory = "kafkaListenerContainerFactory")
    public void consume(Long message) {
        log.info("Consumed message from OrderStatus topic : {}", message);
        execute(message);
    }

    /**
     * Метод обрабатывает сообщение, прочитанное в kafka топике.
     *
     * @param message сообщение.
     */
    private void execute(Long message) {
        var status = orderStatusRepository.findByStatus(EXECUTED_ORDER_STATUS);
        var order = orderRepository.findById(message);
        status.ifPresent(clsOrderStatus ->
                order.ifPresent(o -> o.setStatus(clsOrderStatus)));
    }
}
