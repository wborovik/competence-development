package ru.axbit.domain.repository;

import ru.axbit.domain.repository.common.AbstractRepository;
import ru.axbit.domain.domain.cls.ClsOrderCategory;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс, в котором описываются методы для CRUD операций сущности справочника {@link ClsOrderCategory}.
 */
public interface ClsOrderCategoryRepository extends AbstractRepository<ClsOrderCategory> {
    List<ClsOrderCategory> findAllByDesignationIn(List<String> list);
    Optional<ClsOrderCategory> findByDesignation(String designation);
}
