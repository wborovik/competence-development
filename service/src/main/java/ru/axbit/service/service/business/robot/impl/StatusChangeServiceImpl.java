package ru.axbit.service.service.business.robot.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.axbit.domain.domain.order.WorkOrder;
import ru.axbit.domain.repository.ClsOrderStatusRepository;
import ru.axbit.domain.repository.WorkOrderRepository;
import ru.axbit.service.service.business.processing.producer.OrderStatusProducerService;
import ru.axbit.service.service.business.robot.StatusChangeService;

import javax.transaction.Transactional;

/**
 * Реализация сервиса обработки заказа.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Profile("orderSchedulerService")
public class StatusChangeServiceImpl implements StatusChangeService {
    private final OrderStatusProducerService orderStatusProducerService;

    private static final String PROGRESS_ORDER_STATUS = "progress";
    private static final String EXECUTED_ORDER_STATUS = "executed";

    private final ClsOrderStatusRepository statusRepository;
    private final WorkOrderRepository orderRepository;

    /**
     * Метод меняет статус заказа с new на progress и отправляет сообщение в kafka топик.
     *
     * @param order принимает заказ {@link WorkOrder}
     */
    @Override
    @Transactional
    public void processOrder(WorkOrder order) {
        var status = statusRepository.findByStatus(PROGRESS_ORDER_STATUS);
        status.ifPresent(order::setStatus);
        orderRepository.save(order);
        orderStatusProducerService.sendMessage(order.getId());
    }

    /**
     * Метод обрабатывает сообщение, прочитанное в kafka топике.
     *
     * @param message в сообщении передается идентификатор обрабатываемого заказа.
     */
    @Override
    public void execute(Long message) {
        var status = statusRepository.findByStatus(EXECUTED_ORDER_STATUS);
        var workOrder = orderRepository.findById(message);
        status.ifPresent(clsOrderStatus -> workOrder.ifPresent(order -> {
            order.setStatus(clsOrderStatus);
            orderRepository.save(order);
        }));
    }
}
