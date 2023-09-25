package ru.axbit.domain.repository;

import ru.axbit.domain.domain.transaction.Bill;
import ru.axbit.domain.repository.common.AbstractRepository;

/**
 * Интерфейс, в котором описываются методы для CRUD операций сущности счета на оплату {@link Bill}.
 */
public interface BillRepository extends AbstractRepository<Bill> {
}
