package ru.axbit.domain.domain.common;


import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDateTime;

/**
 * Абстрактный класс аудита сущностей.
 * Содержит: время создания, время изменения, флаг удалён или нет.
 */
@Getter
@Setter
@MappedSuperclass
@FieldNameConstants
public abstract class AuditEntity extends AbstractEntity {
    /**
     * Дата создания сущности.
     */
    @NotNull
    private LocalDateTime created;

    /**
     * Дата изменения сущности.
     */
    private LocalDateTime changed;

    /**
     * Статус является ли сущность удаленной или нет.
     */
    @NotNull
    private boolean deleted;
}
