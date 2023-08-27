package ru.axbit.domain.repository;

import ru.axbit.domain.domain.user.Customer;
import ru.axbit.domain.repository.common.AbstractRepository;

/**
 * Интерфейс, в котором описываются методы для CRUD операций сущности заказчика {@link Customer}.
 */
public interface CustomerRepository extends AbstractRepository<Customer> {
}
