package ru.axbit.domain.repository;

import ru.axbit.domain.domain.order.WorkOrder;
import ru.axbit.domain.repository.common.AbstractRepository;

import java.util.Optional;

/**
 *  Интерфейс, в котором описываются методы для CRUD операций сущности заказа WorkOrder.
 */

public interface WorkOrderRepository extends AbstractRepository<WorkOrder> {
    Optional<WorkOrder> findByIdAndDeletedIsFalse(Long id);
}
