package ru.axbit.domain.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.Query;
import ru.axbit.domain.domain.order.WorkOrder;
import ru.axbit.domain.repository.common.AbstractRepository;

/**
 * Интерфейс, в котором описываются методы для CRUD операций сущности заказа {@link WorkOrder}.
 */

public interface WorkOrderRepository extends AbstractRepository<WorkOrder> {

    @Query("select o from WorkOrder o join ClsOrderStatus os on o.status.id = os.id " +
            "where os.status in :status ")
    Slice<WorkOrder> findAllByStatus(String status, Pageable page);
}
