package ru.axbit.domain.domain.cls.common;

import lombok.Getter;
import lombok.Setter;
import ru.axbit.domain.domain.common.AuditEntity;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * Абстрактный супер-класс для сущностей справочников.
 */
@Getter
@Setter
@MappedSuperclass
@AttributeOverride(name = "id", column = @Column(name = "globalId"))
public abstract class ClsBase extends AuditEntity {
}
