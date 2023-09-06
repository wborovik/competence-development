package ru.axbit.domain.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import ru.axbit.domain.domain.order.WorkOrder;
import ru.axbit.domain.repository.common.AbstractRepository;

import java.util.Optional;

/**
 *  Интерфейс, в котором описываются методы для CRUD операций сущности заказа {@link WorkOrder}.
 */

public interface WorkOrderRepository extends AbstractRepository<WorkOrder> {
    Optional<WorkOrder> findByIdAndDeletedIsFalse(Long id);

    Slice<WorkOrder> findAllByStatusAndDeletedIsFalse(String status, Pageable page);
}
