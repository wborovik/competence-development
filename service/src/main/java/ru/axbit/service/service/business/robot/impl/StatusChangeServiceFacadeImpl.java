package ru.axbit.service.service.business.robot.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.axbit.domain.domain.order.WorkOrder;
import ru.axbit.domain.repository.WorkOrderRepository;
import ru.axbit.service.service.business.robot.StatusChangeService;
import ru.axbit.service.service.business.robot.StatusChangeServiceFacade;
import ru.axbit.service.util.PagingUtils;

/**
 * Реализация фасада сервиса обработки всех заказов.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Profile("orderSchedulerService")
public class StatusChangeServiceFacadeImpl implements StatusChangeServiceFacade {

    private static final String NEW_ORDER_STATUS = "new";
    @Value("${orderSchedulerService.preparation.max-limit-per-request}")
    private int maxTasksLimit;

    private final WorkOrderRepository orderRepository;
    private final StatusChangeService changeService;

    @Override
    public void processAllOrders() {
        PagingUtils.forEachPage(
                pageable -> orderRepository.findAllByStatus(NEW_ORDER_STATUS, pageable),
                this::processEveryOrder,
                maxTasksLimit
        );
    }

    /**
     *
     * @param order передается сущность {@link WorkOrder}
     */
    public void processEveryOrder(WorkOrder order) {
        try {
            changeService.processOrder(order);
        } catch (RuntimeException e) {
            log.error("WorkOrder(id={}) has been failed to process", order.getId(), e);
        }
    }
}
