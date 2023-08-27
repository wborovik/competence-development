package ru.axbit.domain.repository;

import ru.axbit.domain.domain.user.Executor;
import ru.axbit.domain.repository.common.AbstractRepository;

import java.util.Set;

/**
 * Интерфейс, в котором описываются методы для CRUD операций сущности исполнителя {@link Executor}.
 */
public interface ExecutorRepository extends AbstractRepository<Executor> {
    Set<Executor> findAllByIdInAndDeletedIsFalseOrderById(Set<Long> ids);
}
