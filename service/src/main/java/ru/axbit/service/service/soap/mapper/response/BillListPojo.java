package ru.axbit.service.service.soap.mapper.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;
import ru.axbit.domain.domain.transaction.Bill;

/**
 * Класс, содержащий {@link Page} счетов на оплату {@link Bill}.
 * Используется методами, которые делают выборку из БД партиями.
 */
@Getter
@Builder
public class BillListPojo {
    private final Page<Bill> bills;
}
