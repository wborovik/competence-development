package ru.axbit.domain.repository;

import ru.axbit.domain.domain.order.WorkOrder;
import ru.axbit.domain.repository.common.AbstractRepository;

import java.util.Optional;

public interface WorkOrderRepository extends AbstractRepository<WorkOrder> {
    Optional<WorkOrder> findByIdAndDeletedIsFalse(Long id);
}
