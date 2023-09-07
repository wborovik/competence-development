package ru.axbit.service.service.business.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.axbit.service.service.business.robot.StatusChangeServiceFacade;

/**
 * Класс Scheduler, реализующий автоматическое изменение статуса заказа.
 */
@Slf4j
@Component
@RequiredArgsConstructor
@Profile("orderSchedulerService")
public class OrderStatusScheduler {

    private final StatusChangeServiceFacade serviceFacade;

    /**
     * Таймерный метод, передающий сообщения топику kafka.
     */
    @SchedulerLock(name = "statusChangeScheduler_change_task")
    @Scheduled(cron = "${app.scheduling.change-order-status.cron-expression}")
    public void orderStatusChange() {
        log.debug("Start change order status");
        serviceFacade.processAllOrders();
        log.debug("Ended change order status");
    }
}
