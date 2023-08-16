package ru.axbit.domain.repository;

import ru.axbit.domain.domain.user.Executor;
import ru.axbit.domain.repository.common.AbstractRepository;

import java.util.Set;

public interface ExecutorRepository extends AbstractRepository<Executor> {
    Set<Executor> findAllByIdInAndDeletedIsFalse(Set<Long> ids);
}
