package ru.axbit.service.service.business.robot;

import ru.axbit.domain.domain.order.WorkOrder;

/**
 * Сервис обработки заказа.
 */
public interface StatusChangeService {
    void processOrder(WorkOrder order);

    void execute(Long message);
}
