package ru.axbit.domain.repository;

import ru.axbit.domain.domain.cls.ClsOrderStatus;
import ru.axbit.domain.repository.common.AbstractRepository;

import java.util.Optional;

/**
 * Интерфейс, в котором описываются методы для CRUD операций сущности справочника {@link ClsOrderStatus}.
 */
public interface ClsOrderStatusRepository extends AbstractRepository<ClsOrderStatus> {
    Optional<ClsOrderStatus> findByStatus(String status);
}
