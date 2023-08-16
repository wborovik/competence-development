package ru.axbit.domain.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.axbit.domain.domain.user.Executor;
import ru.axbit.domain.repository.common.AbstractRepository;

import java.util.Set;

public interface ExecutorRepository extends AbstractRepository<Executor> {
    Set<Executor> findAllByIdInAndDeletedIsFalseOrderById(Set<Long> ids);
}
