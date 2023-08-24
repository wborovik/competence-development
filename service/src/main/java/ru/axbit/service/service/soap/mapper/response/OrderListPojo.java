package ru.axbit.service.service.soap.mapper.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;
import ru.axbit.domain.domain.order.WorkOrder;

/**
 * Класс, содержащий {@link Page} заказов {@link WorkOrder}.
 * Используется методами, которые делают выборку из БД партиями.
 */
@Getter
@Builder
public class OrderListPojo {
    private final Page<WorkOrder> orders;
}
