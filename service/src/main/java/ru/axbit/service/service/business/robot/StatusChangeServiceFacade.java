package ru.axbit.service.service.business.robot;

/**
 * Фасад сервиса обработки всех заказов {@link ru.axbit.domain.domain.order.WorkOrder}.
 */
public interface StatusChangeServiceFacade {
    void processAllOrders();
}
