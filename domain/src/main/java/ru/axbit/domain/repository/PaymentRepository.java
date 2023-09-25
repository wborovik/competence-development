package ru.axbit.domain.repository;

import ru.axbit.domain.domain.transaction.Payment;
import ru.axbit.domain.repository.common.AbstractRepository;

/**
 * Интерфейс, в котором описываются методы для CRUD операций сущности оплаты счета {@link Payment}.
 */
public interface PaymentRepository extends AbstractRepository<Payment> {
}
