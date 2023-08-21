package ru.axbit.service.service.soap.mapper.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;
import ru.axbit.domain.domain.order.Order;

@Getter
@Builder
public class OrderListPojo {
    private final Page<Order> orders;
}
