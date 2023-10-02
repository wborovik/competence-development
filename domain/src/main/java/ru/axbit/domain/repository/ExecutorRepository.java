package ru.axbit.domain.repository;

import org.springframework.data.jpa.repository.Query;
import ru.axbit.domain.domain.user.Executor;
import ru.axbit.domain.repository.common.AbstractRepository;

import java.util.Optional;
import java.util.Set;

/**
 * Интерфейс, в котором описываются методы для CRUD операций сущности исполнителя {@link Executor}.
 */
public interface ExecutorRepository extends AbstractRepository<Executor> {
    Set<Executor> findAllByIdInAndDeletedIsFalseOrderById(Set<Long> ids);

    @Query("select e from Executor e join ClsOrderCategory oc on e.workCategory.id = oc.id " +
            "where oc.designation = :designation ")
    Optional<Executor> findByWorkCategories(String designation);
}
