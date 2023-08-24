package ru.axbit.service.service.soap.mapper.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;
import ru.axbit.domain.domain.user.Customer;

/**
 * Класс, содержащий {@link Page} заказчиков {@link Customer}.
 * Используется методами, которые делают выборку из БД партиями.
 */
@Getter
@Builder
public class CustomerListPojo {
    private final Page<Customer> customers;
}
