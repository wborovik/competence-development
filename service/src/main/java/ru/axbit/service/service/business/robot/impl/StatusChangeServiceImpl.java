package ru.axbit.service.service.business.robot.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.axbit.domain.domain.order.WorkOrder;
import ru.axbit.domain.repository.ClsOrderStatusRepository;
import ru.axbit.domain.repository.WorkOrderRepository;
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

    private static final String PROGRESS_ORDER_STATUS = "progress";

    private final ClsOrderStatusRepository statusRepository;
    private final WorkOrderRepository orderRepository;

    @Override
    @Transactional
    public void processOrder(WorkOrder order) {
        var orderId = order.getId();



        var status = statusRepository.findByStatus(PROGRESS_ORDER_STATUS);
        status.ifPresent(order::setStatus);
        orderRepository.save(order);
    }
}
