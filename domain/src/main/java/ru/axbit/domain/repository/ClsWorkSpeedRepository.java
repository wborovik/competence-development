package ru.axbit.domain.repository;

import ru.axbit.domain.domain.cls.ClsWorkSpeed;
import ru.axbit.domain.repository.common.AbstractRepository;

import java.util.Optional;

/**
 * Интерфейс, в котором описываются методы для CRUD операций сущности справочника {@link ClsWorkSpeed}.
 */
public interface ClsWorkSpeedRepository extends AbstractRepository<ClsWorkSpeed> {
    Optional<ClsWorkSpeed> findBySpeed(String description);
}
