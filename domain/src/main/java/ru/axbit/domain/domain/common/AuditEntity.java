package ru.axbit.domain.domain.common;


import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Абстрактный класс аудита сущностей.
 * Содержит: время создания, время изменения, флаг удалён или нет.
 */
@Getter
@Setter
@MappedSuperclass
@FieldNameConstants
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditEntity extends AbstractEntity {
    /**
     * Дата создания сущности.
     */
    @NotNull
    @CreatedDate
    private LocalDateTime created;

    /**
     * Дата изменения сущности.
     */
    @LastModifiedDate
    private LocalDateTime changed;

    /**
     * Статус является ли сущность удаленной или нет.
     */
    @NotNull
    private boolean deleted;

    @PrePersist
    private void prePersist() {
        if (Objects.nonNull(changed) && changed.equals(created)) {
            changed = null;
        }
    }

    @PreUpdate
    private void preUpdate() {
        prePersist();
    }
}
